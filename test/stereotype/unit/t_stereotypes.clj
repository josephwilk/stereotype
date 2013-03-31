(ns stereotype.unit.t-stereotypes
  (:use
    [midje.sweet]
    [stereotype.stereotypes]))
    
(facts "stereotype-fn"
  (fact "returns attributes specified with"
    ((stereotype-fn {:x 2})) => {:x 2})
    
  (fact "takes an argument that is merged with orignal attributes"
    ((stereotype-fn {:x 2 :y 1}) {:y 2}) => {:x 2 :y 2}))

(facts "fn-name"
  (fact "it should return the function name"
    (fn-name "test") => (symbol "stereotype-test")))