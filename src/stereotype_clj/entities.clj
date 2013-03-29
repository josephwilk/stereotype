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
      (-> identifier
          name
          symbol
          resolve
          var-get)))