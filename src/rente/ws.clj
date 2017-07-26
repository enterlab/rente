(ns rente.ws
  (:require [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [clojure.core.async :as async]
            [taoensso.sente.server-adapters.http-kit :as sente-http]
            [taoensso.sente :as sente]
            [taoensso.sente.packers.transit :as sente-transit]))

(def ping-counts (atom 0))

(defmulti event-msg-handler :id) ; Dispatch on event-id

(defmethod event-msg-handler :chsk/ws-ping
  [_]
  (let [c (swap! ping-counts inc)]
    (when (zero? (mod c 10))
      (log/info "Ping counts:" c))))

(defmethod event-msg-handler :rente/testevent
  [{:as ev-msg :keys [event id ?data ring-req ?reply-fn send-fn]}]
  (if ?reply-fn
    (?reply-fn [:rente/testevent {:message (str "Hello socket from server Callback, received: " ?data)}])
    (send-fn :sente/all-users-without-uid [:rente/testevent {:message (str "Hello socket from server Event (no callback), received: " ?data)}])))

(defmethod event-msg-handler :default ; Fallback
  [{:as ev-msg :keys [event id ?data ring-req ?reply-fn send-fn]}]
  (let [session (:session ring-req)
        uid     (:uid     session)]
    (log/info "Unhandled event:" event)
    (when ?reply-fn
      (?reply-fn {:umatched-event-as-echoed-from-from-server event}))))

;; Wrap for logging, catching, etc.:
(defn event-msg-handler* [{:as ev-msg :keys [id ?data event]}]
  (event-msg-handler ev-msg))

(defrecord WSRingHandlers [ajax-post-fn ajax-get-or-ws-handshake-fn])

(defrecord WSConnection [ch-recv connected-uids send-fn ring-handlers]
  component/Lifecycle
  (start [component]
    (if (and ch-recv connected-uids send-fn ring-handlers)
      component
      (let [component (component/stop component)
            packer :edn
            {:keys [ch-recv send-fn connected-uids
                    ajax-post-fn ajax-get-or-ws-handshake-fn]}
            (sente/make-channel-socket! sente-http/http-kit-adapter {:packer packer})]
        (log/debug "WebSocket connection started")
        (assoc component
          :ch-recv ch-recv
          :connected-uids connected-uids
          :send-fn send-fn
          :stop-fn (sente/start-chsk-router! ch-recv event-msg-handler*)
          :ring-handlers
          (->WSRingHandlers ajax-post-fn ajax-get-or-ws-handshake-fn)))))
  (stop [component]
    (when-let [stop-fn (:stop-fn component)] (stop-fn))
    (log/debug "WebSocket connection stopped")
    (assoc component
      :ch-recv nil :connected-uids nil :send-fn nil :ring-handlers nil)))


(defn send! [ws-connection user-id event]
  ((:send-fn ws-connection) user-id event))

(defn broadcast! [ws-connection event]
  (let [uids (ws-connection :connected-uids)]
    (doseq [uid (:any @uids)] (send! ws-connection uid event))))

(defn ring-handlers [ws-connection]
  (:ring-handlers ws-connection))


(defn new-ws-connection []
  (map->WSConnection {}))
