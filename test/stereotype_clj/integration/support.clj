(ns stereotype-clj.integration.support
  (:require
    [korma.core          :refer :all]
    [korma.db            :refer :all]
    [clojure.java.jdbc :as sql]
    [stereotype-clj.core :refer :all]))

(defdb mydb {:classname "org.sqlite.JDBC"
             :subprotocol "sqlite"
             :subname "db/test.sqlite3"})
(exec-raw ["CREATE TABLE IF NOT EXISTS users(username TEXT, company TEXT);"])

(defentity users)