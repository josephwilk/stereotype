(ns stereotype-clj.unit.t-sql
  (:use
   [midje.sweet]
   [stereotype-clj.sql]))

(fact "it should detect key from sqlite insert results"
  (pk {(keyword "last_insert_rowid()") 10}) => (contains {:id 10}))

(fact "it should detect key from mysql insert results"
  (pk {:generated_key 11}) => (contains {:id 11}))