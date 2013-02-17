(ns stereotype-clj.core
  (:require
    [korma.db   :refer :all]
    [korma.core :refer :all]))

(def stereotypes (atom {}))
(def sequences (atom {}))
(def sequence-counts (atom {}))

(defn- evaluate-values [map-to-eval]
  (into {} (for [[key-name value] map-to-eval] [key-name (
    let [evaled-value (eval value)]
    
    (if (fn? evaled-value)
      (evaled-value)
      evaled-value))])))

(defn update-stereotypes [new-stereotype]
  (swap! stereotypes merge new-stereotype))

(defmacro defstereotype
  "define a stereotype with default attributes"
  [name attributes]
  (update-stereotypes {name attributes}))

(defn stereotype
  "returns the stereotype defaults"
  [name & [overiding_attributes]]
  
  (let [attributes (merge (name @stereotypes) overiding_attributes)
        evald-attributes (evaluate-values attributes)]
    evald-attributes))

(defn stereotype!
  "returns the stereotype and creates it in the db"
  [name & [overiding_attributes]]

    (let [attributes (stereotype name overiding_attributes)
          evald-attributes (evaluate-values attributes)
          insert-details (insert name (values evald-attributes))]
      evald-attributes))

(defn defsequence [name form]
  (swap! sequence-counts merge {name (atom 0)})
  (swap! sequences merge {name form}))

(defn generate [type]
  (let [form (type @sequences)
        next-sequence (swap! (type @sequence-counts) inc)]
    (form next-sequence)))