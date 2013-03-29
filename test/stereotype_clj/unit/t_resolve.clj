(ns stereotype-clj.unit.t-resolve
  (:require
   [midje.sweet :refer :all]
   [stereotype-clj.resolve :refer :all]))

(fact "it should resolve unparameterized functions"
  (all {:123 (fn [] 321)}) => {:123 321})

(fact "it should resolve parameterized functions"
  (all {:123 (fn [x] 321)}) => {:123 321})

(fact "it should pass resolved values to parameterized function"
  (all {:x 123 :y (fn [x] (:x x))}) => {:x 123 :y 123})

(fact "it should resolve unparameterized functions before parameterized functions"
  (all {:x (fn [] 123) :y (fn [x] (:x x))}) => {:x 123 :y 123})

(fact "it should raise an error with a parameterized function with > 1 arguments"
  (all {:y (fn [x y] (:x x))}) => (throws clojure.lang.ArityException))