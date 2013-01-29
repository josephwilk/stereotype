(ns stereotype-clj.core
  (:require
    [korma.db   :refer :all]
    [korma.core :refer :all]))

(defn evaluate-values [m]
  (into {} (for [[k v] m] [k (eval v)])))

(def sterotypes (atom {}))

(defn update-sterotypes [new-sterotype]
  (reset! sterotypes (merge @sterotypes new-sterotype)))

(defmacro defsterotype
  "define a sterotype with default attributes"
  [name attributes]
  (update-sterotypes {name attributes}))

(defn sterotype
  "returns the sterotype defaults"
  [name & [overiding_attributes]]
  (merge (name @sterotypes) overiding_attributes))

(defn sterotype!
  "returns the sterotype and creates it in the db"
  [name & [overiding_attributes]]

  (let [attributes (sterotype name overiding_attributes)
        evald-attributes (evaluate-values attributes)]
    (insert name (values evald-attributes))
    attributes))