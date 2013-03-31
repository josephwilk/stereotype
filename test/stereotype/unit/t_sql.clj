(ns stereotype.unit.t-sql
  (:use
   [midje.sweet]
   [stereotype.sql]))

(fact "it should detect key from sqlite insert results"
  (pk {(keyword "last_insert_rowid()") 10}) => (contains {:id 10}))

(fact "it should detect key from mysql insert results"
  (pk {:generated_key 11}) => (contains {:id 11}))

(fact "it should detect key from postgres insert results"
  (pk {:id 11}) => (contains {:id 11}))