(ns stereotype.db)

(defprotocol StereotypeDb
  (insert [db table values]))

(defn do-insert [s-db table attrs]
  (insert s-db table attrs))
