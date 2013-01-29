(ns stereotype-clj.unit.t-core
  (:require
    [midje.sweet :refer :all]
    [stereotype-clj.core :refer :all]
    [korma.db   :refer :all]
    [korma.core :refer :all]))

(background (before :facts (update-stereotypes {})))

(facts "defining defaults for a stereotype"
  (fact "it should return the user entity defaults"
    (defstereotype :user {:name "josephwilk"})
    (stereotype :user) => {:name "josephwilk"})

  (fact "it should return the monkey entity defaults"
    (defstereotype :monkey {:name "baboon"})
    (stereotype :monkey) => {:name "baboon"})

  (fact "it should overide defaults when they are specified"
    (defstereotype :employee {:name "josephwilk" :company "soundcloud"})
    (stereotype :employee {:name "monkey"}) => {:name "monkey" :company "soundcloud"}))