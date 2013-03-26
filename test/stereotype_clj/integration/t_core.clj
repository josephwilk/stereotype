(ns stereotype-clj.integration.t-core
  (:use
    [midje.sweet]
    [stereotype-clj.core]
    [stereotype-clj.integration.support]
    [stereotype-clj.integration.stereotypes]
    [korma.db]
    [korma.core]))

(namespace-state-changes [
  (around :facts (transaction ?form (rollback)))
  (before :facts (init))])

(facts "stereotype!"
  (fact "it should raise an error on an invalid stereotype key"
   (stereotype! :made-up) => (throws Exception #":made-up"))

  (fact "it should create a record in the database with default values"
    (stereotype! :admin-users {:company "soundcloud"})

    (select-keys (first (select admin-users)) [:username :company]) => {:username "josephwilk"
                                                                        :company "soundcloud"}
    (:date_of_birth (first(select admin-users))) => #"^\d+-\d+-\d+")

  (future-facts "it should return the attributes used to create record"
    ;This currently has a __inserted_key_id__ field which needs to get removed on last call.
    ;Challenge is knowing we have reached the last of any chained creations.
    (stereotype! :users {:name "alicenolawilk"}) => {:id 1, :name "alicenolawilk", "address_id" 1})

  (fact "stereotypes create their associations"
    (stereotype! users)
    ((first (select users (with address))) :postcode ) => "1234"))