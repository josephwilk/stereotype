(ns stereotype-clj.integration.t-core
  (:require
    [midje.sweet :refer :all]
    [stereotype-clj.core :refer :all]
    [stereotype-clj.integration.support :refer :all]
    [korma.core :refer :all]))

(defentity users)
(defsterotype :users {:username "josephwilk"})

(background (before :contents (exec-raw ["DELETE FROM users;"])))
    
(facts "sterotype!"
  (fact "it should create a record in the database with default values"
    (sterotype! :users)
    (first (select users)) => {:username "josephwilk"}))