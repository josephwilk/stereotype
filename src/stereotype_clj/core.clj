(ns stereotype-clj.core
  (:require
    [korma.db   :refer :all]
    [korma.core :refer :all]))

(defn evaluate-values [m]
  (into {} (for [[k v] m] [k (eval v)])))

(def stereotypes (atom {}))

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
        evald-attributes (evaluate-values attributes)]
    (insert name (values evald-attributes))
    attributes))