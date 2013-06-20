(ns stereotype.integration.t-core-with-jdbc
  (:require
   [clojure.java.jdbc     :as j]
   [clojure.java.jdbc.sql :as s]
   [stereotype.db.jdbc]
   [clj-time.core       :as time])
  (:use
   [midje.sweet]
   [stereotype.core])
  (:import
   [stereotype.db.jdbc JDBC]))

(def config
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "test/fixtures/db/test.sqlite3"})

(def ^:dynamic mydb config)

(defstereotypedb (JDBC. mydb))

(defn init []
  (defsequence :email #(str "joe" % "@test.com"))

  (defstereotype :admin_users {:username "josephwilk"
                               :date_of_birth #(time/now)
                               :email #(generate :email)
                               :company "monkeys"
                               :urn (fn [user] (str (:company user) (:username user)))}))


(namespace-state-changes [(around :facts (j/db-transaction [test-db mydb]
                                                           (j/db-set-rollback-only! test-db)
                                                           (binding [mydb test-db] ?form)))
                          (before :facts (init))])

(facts "stereotype!"
  (fact "it should create a record in the database with default values"
    (stereotype! :admin_users {:company "soundcloud"})

    (select-keys (first (j/query mydb (s/select * :admin_users))) [:username :company]) =>
    {:username "josephwilk"  :company "soundcloud"}))
