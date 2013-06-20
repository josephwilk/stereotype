(ns stereotype.integration.t-core
  (:require [stereotype.db.korma])
  (:use
    [midje.sweet]
    [stereotype.core]
    [stereotype.integration.support]
    [stereotype.integration.stereotypes]
    [korma.db]
    [korma.core])
  (:import [stereotype.db.korma Korma]))

(namespace-state-changes [
  (around :facts (transaction ?form (rollback)))
  (before :facts (init))])


(defstereotype address {:postcode "1234"})

(defstereotype users {:name "josephwilk"
                      :address #(stereotype! address {} (Korma. mydb))})

(facts "stereotype!"
  (fact "it should raise an error on an invalid stereotype key"
   (stereotype! :made-up {} (Korma. mydb)) => (throws Exception #":made-up"))

  (fact "it should create a record in the database with default values"
    (stereotype! :admin-users {:company "soundcloud"} (Korma. mydb))

    (select-keys (first (select admin-users)) [:username :company]) => {:username "josephwilk"
                                                                        :company "soundcloud"}
    (:date_of_birth (first(select admin-users))) => #"^\d+-\d+-\d+")

  (fact "it should return the attributes used to create record"
    (stereotype! :users {:name "alicenolawilk"} (Korma. mydb)) => {:id 1, :name "alicenolawilk", "address_id" 1})

  (fact "stereotypes create their associations"
    (stereotype! users {} (Korma. mydb))
    ((first (select users (with address))) :postcode ) => "1234"))
