(ns stereotype-clj.integration.t-core
  (:require
    [midje.sweet :refer :all]
    [stereotype-clj.core :refer :all]
    [clojure.java.jdbc :as sql]
    [korma.db   :refer :all]
    [korma.core :refer :all]))

(defdb mydb {:classname "org.sqlite.JDBC"
             :subprotocol "sqlite"
             :subname "db/test.sqlite3"})

(exec-raw ["CREATE TABLE IF NOT EXISTS users(username VARCHAR(255));"])

(defentity users)

(background (before :contents (exec-raw ["DELETE FROM users;"])))
    
(facts "sterotype!"
  (fact "it should create a record in the database with default values"
    (defsterotype :users {:username "josephwilk"})
    (sterotype! :users)
    (first (select users)) => {:username "josephwilk"}))