(ns stereotype-clj.stereotypes
  (:require
    [korma.db   :refer :all]
    [korma.core :refer :all]))

(def stereotypes (atom {}))

(defn arg-count [f]
  (let [m (first (.getDeclaredMethods (class f)))
     p (.getParameterTypes m)]
    (alength p)))

(defn- evaluate-values [map-to-eval]
  (into {} (for [[key-name value] map-to-eval] [key-name (
    let [evaled-value (eval value)]

    (if (fn? evaled-value)
      (cond
        (> (arg-count evaled-value) 0) (evaled-value {})
        :else (evaled-value))
      evaled-value))])))

(defn update-stereotypes [new-stereotype]
  (swap! stereotypes merge new-stereotype))
  
(defn define [name attributes]
  (update-stereotypes {name attributes}))
  
(defn build [name & [overiding_attributes]]
  (let [attributes (merge (name @stereotypes) overiding_attributes)
        evald-attributes (evaluate-values attributes)]
        evald-attributes))
  
(defn build-and-insert [name & [overiding_attributes]]
  (let [attributes (build name overiding_attributes)
        insert-details (insert name (values attributes))]
    attributes))