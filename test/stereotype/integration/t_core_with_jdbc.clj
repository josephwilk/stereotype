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
   :subname     "test/fixtures/db/test.sqlite3"
   })

(def ^:dynamic blocking config)

(defn init []
  (defsequence :email #(str "joe" % "@test.com"))

  (defstereotype :admin_users {:username "josephwilk"
                               :date_of_birth #(time/now)
                               :email #(generate :email)
                               :company "monkeys"
                               :urn (fn [user] (str (:company user) (:username user)))}))


(namespace-state-changes [(around :facts (j/db-transaction [test-db blocking]
                                                           (j/db-set-rollback-only! test-db)
                                                           (binding [blocking test-db] ?form)))
                          (before :facts (init))])

(facts "stereotype!"
  (fact "it should create a record in the database with default values"
    (stereotype! :admin_users {:company "soundcloud"} (JDBC. blocking))

    (select-keys (first (j/query blocking (s/select * :admin_users))) [:username :company]) =>
    {:username "josephwilk"  :company "soundcloud"}))
