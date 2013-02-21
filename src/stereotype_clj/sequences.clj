(ns stereotype-clj.sequences)

(def sequences (atom {}))
(def sequence-counts (atom {}))
  
(defn create [name form]
  (swap! sequence-counts merge {name (atom 0)})
  (swap! sequences merge {name form}))
  
(defn generate [type]
  (let [form (type @sequences)
        next-sequence (swap! (type @sequence-counts) inc)]
    (form next-sequence)))