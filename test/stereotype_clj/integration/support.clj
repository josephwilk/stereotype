(ns stereotype-clj.integration.support
  (:require
    [korma.core          :refer :all]
    [korma.db            :refer :all]
    [clojure.java.jdbc   :as sql]
    [stereotype-clj.core :refer :all]))

(def config
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "tmp/db/test.sqlite3"
  })

(sql/with-connection config
  (clojure.java.jdbc/do-commands (str "CREATE TABLE IF NOT EXISTS users(username TEXT, date_of_birth DATETIME, company TEXT, urn TEXT);")))

(defdb mydb config)

(defentity users)