(ns stereotype.db.jdbc
  (:require
   [stereotype.db :refer :all]
   [clojure.java.jdbc :as j]))

(deftype JDBC [db]
  StereotypeDb
  (insert [_ table attributes]
    (first (j/insert! db table attributes))))

(defn with [db]
  (JDBC. db))
