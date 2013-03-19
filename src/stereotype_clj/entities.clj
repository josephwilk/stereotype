(ns stereotype-clj.entities)

(defn id-for [identifier]
  (cond
    (map? identifier)
    (:name identifier)

    :else
      identifier))

(defn entity-for [identifier]
  (cond
    (map? identifier)
    identifier

    :else
      (var-get (resolve (symbol (name identifier))))))

(defn primary-key-name [entity]
  (:pk entity))

(def insertion-key
  (keyword "last_insert_rowid()"))

(defn extract-key [insert]
  (insert insertion-key))

(defn insertion? [value]
  (and
   (map? value)
   (contains? value insertion-key)))