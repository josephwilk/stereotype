(ns stereotype-clj.unit.t-core
  (:use
    [midje.sweet :refer :all]
    [stereotype-clj.core]))

(facts "defining defaults for a sterotype"
  (fact "it should return a map with default values"
    (defsterotype :user {:name "josephwilk"})
    (sterotype :user) => {:name "josephwilk"})

  (fact "it should return a map with default values"
    (defsterotype :user {:name "josephwilk" :company "soundcloud"})
    (sterotype :user {:name "monkey"}) => {:name "monkey" :company "soundcloud"}))