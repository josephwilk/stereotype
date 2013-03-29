(ns stereotype-clj.core
  (:require
    [stereotype-clj.stereotypes :as stereotypes]
    [stereotype-clj.sequences   :as sequences]))

(defmacro defstereotype
  "define a stereotype with default attributes"
  [stereotype-id attributes]
  (stereotypes/define stereotype-id attributes))

(defn stereotype
  "returns the stereotype defaults"
  [stereotype-id & [overiding_attributes]]
  (stereotypes/build stereotype-id overiding_attributes))

(defn stereotype!
  "returns the stereotype and creates it in the db"
  [stereotype-id & [overiding_attributes]]
  (stereotypes/build-and-insert stereotype-id overiding_attributes))

(defmacro defsequence
  "create a form which will be used to generate a sequence"
  [sequence-id form]
  (sequences/define sequence-id form))

(defn generate
  "generate the next value in the sequence"
  [type]
  (sequences/generate type))