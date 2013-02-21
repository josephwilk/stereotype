(ns stereotype-clj.core
  (:require
    [stereotype-clj.stereotypes :as stereotypes]
    [stereotype-clj.sequences   :as sequences]))

(defn defstereotype
  "define a stereotype with default attributes"
  [name attributes]
  (stereotypes/create name attributes))

(defn stereotype
  "returns the stereotype defaults"
  [name & [overiding_attributes]]
  (stereotypes/build name overiding_attributes))

(defn stereotype!
  "returns the stereotype and creates it in the db"
  [name & [overiding_attributes]]
    (stereotypes/build-and-insert name overiding_attributes))

(defn defsequence
  "create a form which will be used to generate a sequence"
  [name form]
  (sequences/create name form))

(defn generate
  "generate the next value in the sequence"
  [type]
  (sequences/generate type))