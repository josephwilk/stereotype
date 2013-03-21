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