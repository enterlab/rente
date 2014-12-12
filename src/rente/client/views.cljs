(ns rente.client.views
  (:require [rente.client.ws :as socket]))

(defn main [data]
  [:div
   [:h1 (:title @data)]
   [:span "Hello world! This is reagent!"]
   [:br]
   [:span "And sente seems to work too.."]
   [:br]
   [:span "And figwheel.. w00t!"]
   [:br]
   [:button {:on-click socket/test-socket-callback} "Send Message Callback"]
   [:br]
   [:button {:on-click socket/test-socket-event} "Send Message Event"]
   ])

