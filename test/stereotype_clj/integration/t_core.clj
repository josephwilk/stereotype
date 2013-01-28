(ns stereotype-clj.integration.t-core
  (:require
    [midje.sweet :refer :all]
    [stereotype-clj.core :refer :all]
    [stereotype-clj.integration.support :refer :all]
    [korma.core :refer :all]))

(background (before :contents (exec-raw ["DELETE FROM users;"])))

(facts "sterotype!"
  (fact "it should create a record in the database with default values"
    (sterotype! :users {:company "soundcloud"})
    (first (select users)) => {:username "josephwilk" :company "soundcloud"})

  (fact "it should return the attributes used to create record"
    (sterotype! :users {:company "soundcloud"}) => {:username "josephwilk" :company "soundcloud"}))