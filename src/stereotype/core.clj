(ns stereotype.core
  (:use
   [slingshot.slingshot :only [throw+]])
  (:require
   [stereotype.stereotypes :as stereotypes]
   [stereotype.db          :as db]
   [stereotype.sequences   :as sequences]))

(defn defstereotypedb [db]
  (reset! stereotype.db/stereotype-db db))

(defmacro defstereotype
  "define a stereotype with default attributes"
  [stereotype-id attributes]
  `(defn ~(stereotypes/fn-name stereotype-id) [overiding-attributes#]
     (apply (stereotypes/stereotype-fn ~attributes)
            [overiding-attributes#])))

(defn stereotype
  "returns the stereotype defaults"
  [stereotype-id & [overiding_attributes]]
  (stereotypes/build stereotype-id overiding_attributes))

(defn stereotype!
  "returns the stereotype and creates it in the db"
  ([stereotype-id] (stereotype! stereotype-id {}))
  ([stereotype-id overiding_attributes] (stereotype! stereotype-id overiding_attributes @stereotype.db/stereotype-db))
  ([stereotype-id overiding_attributes db]
     (when-not (or db @stereotype.db/stereotype-db)
       (throw+ {:type ::undefined-database
                :message "No database set! Use (defstereotypedb your-db)"}))
     (stereotypes/build-and-insert stereotype-id overiding_attributes db)))

(defmacro defsequence
  "create a form which will be used to generate a sequence"
  [sequence-id form]
  `(defn ~(sequences/fn-name sequence-id) []
     (apply (sequences/sequence-fn ~sequence-id ~form) [])))

(defn reset-sequence!
  "Reset the counter to 1 for specified sequence"
  [sequence-id]
  (sequences/reset-for! sequence-id))

(defn reset-all-sequences!
  "Reset all counters to 1"
  []
  (sequences/reset-all!))

(defn generate
  "generate the next value in the sequence"
  [sequence-id]
  (sequences/generate sequence-id))
