(ns stereotype-clj.core
  (:require
    [korma.db   :refer :all]
    [korma.core :refer :all]))

(def stereotypes (atom {}))

(defn- evaluate-values [map-to-eval]
  (into {} (for [[key-name value] map-to-eval] [key-name (
    let [evaled-value (eval value)]
    (if (ifn? evaled-value)
      (evaled-value)
      evaled-value))])))

(defn update-stereotypes [new-stereotype]
  (reset! stereotypes (merge @stereotypes new-stereotype)))

(defmacro defstereotype
  "define a stereotype with default attributes"
  [name attributes]
  (update-stereotypes {name attributes}))

(defn stereotype
  "returns the stereotype defaults"
  [name & [overiding_attributes]]
  (merge (name @stereotypes) overiding_attributes))

(defn stereotype!
  "returns the stereotype and creates it in the db"
  [name & [overiding_attributes]]

    (let [attributes (stereotype name overiding_attributes)
          evald-attributes (evaluate-values attributes)
          insert-details (insert name (values evald-attributes))]
      evald-attributes))