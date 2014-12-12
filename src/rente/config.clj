(ns rente.config
  (:require [environ.core :refer [env]]))

(defn get-config []
  {:port (Integer/parseInt (env :rente-port))})
