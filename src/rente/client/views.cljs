(ns rente.client.views)

(defn main [data]
  [:div
   [:h1 (:title @data)]
   [:span "Hello world! This is reagent!"]
   [:br]
   [:span "And sente seems to work too.."]
   [:br]
   [:span "And figwheel.. w00t!"]
   ])

