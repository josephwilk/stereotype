(ns stereotype-clj.integration.t-core
  (:use
    [midje.sweet]
    [stereotype-clj.core]
    [stereotype-clj.integration.support]
    [korma.db]
    [korma.core]))

(background (around :facts (transaction ?form (rollback))))

(facts "stereotype!"
  (fact "it should raise an error on an invalid stereotype key"
   (stereotype! :made-up) => (throws Exception #":made-up not found"))

  (fact "it should create a record in the database with default values"
    (stereotype! :admin-users {:company "soundcloud"})

    (select-keys (first (select admin-users)) [:username :company]) => {:username "josephwilk"
                                                                        :company "soundcloud"}
    (:date_of_birth (first(select admin-users))) => #"^\d+-\d+-\d+")

  (fact "it should return the attributes used to create record"
    (select-keys (stereotype! :admin-users {:company "soundcloud"}) [:username :company]) => {:username "josephwilk"
                                                                                              :company "soundcloud"}))