(ns stereotype-clj.integration.support
  (:use
    [korma.core]
    [korma.db]
    [stereotype-clj.core])
  (:require
   [clojure.java.jdbc :as sql]))

(def config
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "test/fixtures/db/test.sqlite3"
  })

(sql/with-connection config
  (clojure.java.jdbc/do-commands (str "CREATE TABLE IF NOT EXISTS admin_users(username TEXT, date_of_birth DATETIME, email TEXT, company TEXT, urn TEXT);")))

(defdb mydb config)

(defentity admin-users (table :admin_users))