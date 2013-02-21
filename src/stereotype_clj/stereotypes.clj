(ns stereotype-clj.stereotypes
  (:require
    [korma.db   :refer :all]
    [korma.core :refer :all]))

(def stereotypes (atom {}))
  
(defn- evaluate-values [map-to-eval]
  (into {} (for [[key-name value] map-to-eval] [key-name (
    let [evaled-value (eval value)]
    
    (if (fn? evaled-value)
      (evaled-value)
      evaled-value))])))

(defn update-stereotypes [new-stereotype]
  (swap! stereotypes merge new-stereotype))
  
(defn create [name attributes]
  (update-stereotypes {name attributes}))
  
(defn build [name & [overiding_attributes]]
  (let [attributes (merge (name @stereotypes) overiding_attributes)
        evald-attributes (evaluate-values attributes)]
        evald-attributes))
  
(defn build-and-insert [name & [overiding_attributes]]
  (let [attributes (build name overiding_attributes)
        insert-details (insert name (values attributes))]
    attributes))