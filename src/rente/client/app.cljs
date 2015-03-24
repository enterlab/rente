(ns rente.client.app
    (:require-macros [cljs.core.async.macros :refer [go-loop]])
    (:require [reagent.core :as reagent]
              [rente.client.views :as views]
              [rente.client.ws :as ws]))

(defonce state (reagent/atom {:title "RENTE"
                              :messages []

                              :re-render-flip false}))


(defmulti handle-event (fn [data [ev-id ev-data]] ev-id))

(defmethod handle-event :default
  [data [_ msg]]
  (swap! data update-in [:messages] #(conj % msg)))

(defn app [data]
  (:re-render-flip @data)
  [views/main data])

(defn ^:export main []
  (when-let [root (.getElementById js/document "app")]
    (reagent/render-component [app state] root)))
