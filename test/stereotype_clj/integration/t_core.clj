(ns stereotype-clj.integration.t-core
  (:require
    [midje.sweet                        :refer :all]
    [stereotype-clj.core                :refer :all]
    [stereotype-clj.integration.support :refer :all]
    [korma.db                           :refer :all]
    [korma.core                         :refer :all]))

(background (around :facts (transaction ?form (rollback))))

(facts "stereotype!"
  (fact "it should create a record in the database with default values"
    (stereotype! :users {:company "soundcloud"})
    (select-keys (first (select users)) [:username :company]) => {:username "josephwilk"
                                                                  :company "soundcloud"}
    (:date_of_birth (first(select users))) => #"^\d+-\d+-\d+")

  (fact "it should return the attributes used to create record"
    (select-keys (stereotype! :users {:company "soundcloud"}) [:username :company]) => {:username "josephwilk"
                                                                                        :company "soundcloud"}))