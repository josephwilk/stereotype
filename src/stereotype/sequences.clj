(ns stereotype.sequences
  (:use
    [slingshot.slingshot :only [throw+]]))

(def sequence-counts (atom {}))

(defn fn-name [sequence-id]
  (->> sequence-id
       name
       (str "sequence-")
       symbol))

(defn- sequence-for [sequence-id]
  (let [sequence-fn (resolve (fn-name sequence-id))]
    (when-not sequence-fn
      (throw+ {:type ::undefined-sequence
               :stereotype sequence-id}))
    sequence-fn))

(defn reset-for! [sequence-id]
  (swap! sequence-counts merge {sequence-id (atom 0)}))

(defn reset-all! []
  (dorun
    (map reset-for! (keys @sequence-counts))))

(defn sequence-fn [sequence-id form]
  (fn []
    (when-not (sequence-id @sequence-counts) (reset-for! sequence-id))
    (swap! (sequence-id @sequence-counts) inc)
    (apply form [@(sequence-id @sequence-counts)])))

(defn generate [sequence-id]
  ((sequence-for sequence-id)))
