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
    (first (select users)) => {:username "josephwilk" :company "soundcloud"})

  (fact "it should return the attributes used to create record"
    (stereotype! :users {:company "soundcloud"}) => {:username "josephwilk" :company "soundcloud"}))