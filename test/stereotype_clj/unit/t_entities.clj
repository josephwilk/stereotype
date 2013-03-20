(ns stereotype-clj.unit.t-entities
  (:use
   [midje.sweet]
   [stereotype-clj.entities]))

(fact "it should detect key from sqlite insert results"
  (with-pk {} {(keyword "last_insert_rowid()") 10}) => (contains {:id 10}))

(fact "it should detect key from mysql insert results"
  (with-pk {} {:generated_key 11}) => (contains {:id 11}))