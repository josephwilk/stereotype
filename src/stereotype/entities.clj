(ns stereotype.entities)

(defn id-for [identifier]
  (cond
    (map? identifier)
    (:name identifier)
    :else
      identifier))

(defn- var-called [identifier]
  (let [possible-var
        (-> identifier
            name
            symbol
            resolve)]
    (if possible-var
      (var-get possible-var)
      identifier)))

(defn entity-for [identifier]
  (cond
    (map? identifier)
    identifier
    :else
    (var-called identifier)))
