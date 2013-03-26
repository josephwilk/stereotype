(ns stereotype-clj.unit.t-core
  (:use
    [midje.sweet]
    [stereotype-clj.core]))

(facts "defining defaults for a stereotype"
  (fact "it should return the user entity defaults"
    (defstereotype :user {:name "josephwilk"})
    (stereotype :user) => {:name "josephwilk"})

  (fact "it should return the monkey entity defaults"
    (defstereotype :monkey {:name "baboon"})
    (stereotype :monkey) => {:name "baboon"})

  (fact "it should overide defaults when they are specified"
    (defstereotype :employee {:name "josephwilk" :company "soundcloud"})
    (stereotype :employee {:name "monkey"}) => {:name "monkey" :company "soundcloud"})

  (fact "it should raise an error on referencing an undefined stereotype"
    (stereotype :something-made-up) => (throws Exception #":something-made-up")))

(facts "sequences"
  (fact "it should generate unique emails"
    (defsequence :email #(str "person" % "@example.com"))
    (defstereotype :user {:email #(generate :email)})

    (stereotype :user) => {:email "person1@example.com"}
    (stereotype :user) => {:email "person2@example.com"})

  (fact "it should support multiple sequences"
    (defsequence :email #(str "person" % "@example.com"))
    (defsequence :rank #(str "rank:" %))

    (defstereotype :user   {:email #(generate :email)})
    (defstereotype :ranker {:rank #(generate :rank)})

    (stereotype :user)   => {:email "person1@example.com"}
    (stereotype :user)   => {:email "person2@example.com"}
    (stereotype :ranker) => {:rank "rank:1"}))
