(ns stereotype.integration.t-core-with-korma
  (:require
   [stereotype.db.korma]
   [clj-time.core :as time])
  (:use
    [midje.sweet]
    [stereotype.core]
    [stereotype.integration.support]
    [korma.db]
    [korma.core])
  (:import [stereotype.db.korma Korma]))

(defstereotypedb (Korma. mydb))

(defn init []
  (defsequence :email #(str "joe" % "@test.com"))
  (defstereotype :admin-users {:username "josephwilk"
                               :date_of_birth #(time/now)
                               :email #(generate :email)
                               :company "monkeys"
                               :urn (fn [user] (str (:company user) (:username user)))})

  (defstereotype address {:postcode "1234"})
  (defstereotype users {:name "josephwilk"
                        :address #(stereotype! address {})}))

(namespace-state-changes [(around :facts (transaction ?form (rollback)))
                          (before :facts (init))])

(facts "stereotype!"
  (fact "it should raise an error on an invalid stereotype key"
    (stereotype! :made-up {}) => (throws Exception #":made-up"))

  (fact "it should create a record in the database with default values"
    (stereotype! :admin-users {:company "soundcloud"})

    (select-keys (first (select admin-users)) [:username :company]) => {:username "josephwilk"
                                                                        :company "soundcloud"}
    (:date_of_birth (first(select admin-users))) => #"^\d+-\d+-\d+")

  (fact "it should return the attributes used to create record"
    (stereotype! :users {:name "alicenolawilk"}) => {:id 1, :name "alicenolawilk", "address_id" 1})

  (fact "stereotypes create their associations"
    (stereotype! users {})
    (:postcode (first (select users (with address)))) => "1234"))
