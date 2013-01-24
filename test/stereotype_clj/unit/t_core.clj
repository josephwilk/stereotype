(ns stereotype-clj.unit.t-core
  (:use
    [midje.sweet :refer :all]
    [stereotype-clj.core]))

(background (before :facts (update-sterotypes {})))

(facts "defining defaults for a sterotype"
  (fact "it should return the user entity defaults"
    (defsterotype :user {:name "josephwilk"})
    (sterotype :user) => {:name "josephwilk"})

  (fact "it should return the monkey entity defaults"
    (defsterotype :monkey {:name "baboon"})
    (sterotype :monkey) => {:name "baboon"})

  (fact "it should overide defaults when they are specified"
    (defsterotype :user {:name "josephwilk" :company "soundcloud"})
    (sterotype :user {:name "monkey"}) => {:name "monkey" :company "soundcloud"}))