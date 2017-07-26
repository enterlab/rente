(ns rente.client.views
  (:require [rente.client.ws :as socket]))

(defn main [data]
  [:div
   [:h1 (:title @data)]
   [:div "Hello world! This is reagent speaking!"]
   [:br]
   [:div "Look in your browser's developer console to see the web socket communication when clicking below buttons."]
   [:br]
   [:button {:on-click socket/test-socket-callback} "Send Message Callback"]
   [:br]
   [:button {:on-click socket/test-socket-event} "Send Message Event"]])
