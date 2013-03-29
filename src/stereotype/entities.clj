(ns stereotype.entities)

(defn id-for [identifier]
  (cond
    (map? identifier)
    (:name identifier)

    :else
      identifier))

(defn- var-called [identifier]
  (-> identifier
      name
      symbol
      resolve
      var-get))

(defn entity-for [identifier]
  (cond
    (map? identifier)
    identifier
    :else
    (var-called identifier)))