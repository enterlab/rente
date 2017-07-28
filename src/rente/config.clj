(ns rente.config
  (:require [environ.core :refer [env]]))

(defn get-config []
  {:port (Integer/parseInt (env :port "8080"))})
