(ns stereotype-clj.unit.t-core
  (:require
    [midje.sweet :refer :all]
    [stereotype-clj.core :refer :all]
    [korma.db   :refer :all]
    [korma.core :refer :all]))

(background (before :facts (update-sterotypes {})))

(facts "defining defaults for a sterotype"
  (fact "it should return the user entity defaults"
    (defsterotype :user {:name "josephwilk"})
    (sterotype :user) => {:name "josephwilk"})

  (fact "it should return the monkey entity defaults"
    (defsterotype :monkey {:name "baboon"})
    (sterotype :monkey) => {:name "baboon"})

  (fact "it should overide defaults when they are specified"
    (defsterotype :employee {:name "josephwilk" :company "soundcloud"})
    (sterotype :employee {:name "monkey"}) => {:name "monkey" :company "soundcloud"}))