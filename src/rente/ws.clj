(ns rente.ws
  (:require [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [clojure.core.async :as async]
            [taoensso.sente :as sente]
            [taoensso.sente.packers.transit :as sente-transit]))

(defrecord WSRingHandlers [ajax-post-fn ajax-get-or-ws-handshake-fn])

(defrecord WSConnection [ch-recv connected-uids send-fn ring-handlers]
  component/Lifecycle
  (start [component]
    (if (and ch-recv connected-uids send-fn ring-handlers)
      component
      (let [component (component/stop component)

            packer (sente-transit/get-flexi-packer :edn)

            {:keys [ch-recv send-fn connected-uids
                    ajax-post-fn ajax-get-or-ws-handshake-fn]}
            (sente/make-channel-socket! {:packer packer})]
        (log/debug "WebSocket connection started")
        (assoc component
          :ch-recv ch-recv
          :connected-uids connected-uids
          :send-fn send-fn
          :ring-handlers
          (->WSRingHandlers ajax-post-fn ajax-get-or-ws-handshake-fn)))))
  (stop [component]
    (when ch-recv (async/close! ch-recv))
    (log/debug "WebSocket connection stopped")
    (assoc component
      :ch-recv nil :connected-uids nil :send-fn nil :ring-handlers nil)))


(defn send! [ws-connection user-id event]
  ((:send-fn ws-connection) user-id event))

(defn broadcast! [ws-connection event]
  (let [uids (ws-connection :connected-uids )]
    (doseq [uid (:any @uids)] (send! ws-connection uid event))))

(defn ring-handlers [ws-connection]
  (:ring-handlers ws-connection))


(defn new-ws-connection []
  (map->WSConnection {}))
