(ns stereotype.db)

(defonce stereotype-db (atom nil))

(defprotocol StereotypeDb
  (insert [db table values]))

(defn do-insert [s-db table attrs]
  (insert s-db table attrs))
