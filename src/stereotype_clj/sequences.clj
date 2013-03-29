(ns stereotype-clj.sequences
  (:use
    [slingshot.slingshot :only [throw+]]))

(def sequence-counts (atom {}))

(defn- fn-name [sequence-id]
  (symbol (str "sequence-" (name sequence-id))))

(defn- sequence-for [sequence-id]
  (let [sequence-fn (resolve (fn-name sequence-id))]
    (when-not sequence-fn
      (throw+ {:type ::undefined-sequence
               :stereotype sequence-id}))
    sequence-fn))
  
(defn define [sequence-id form]
  (swap! sequence-counts merge {sequence-id (atom 0)})
  `(defn ~(fn-name sequence-id) []
     (swap! (~sequence-id @sequence-counts) inc)
     (apply ~form [@(~sequence-id @sequence-counts)])))
  
(defn generate [sequence-id]
  ((sequence-for sequence-id)))