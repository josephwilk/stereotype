(ns stereotype.unit.t-sequences
  (:use
    [midje.sweet]
    [stereotype.sequences]))
    
(fact "generates a sequence"
  (let [sequence-fn (sequence-fn :test #(str "start-" % "-end"))]
    (sequence-fn) => "start-1-end"
    (sequence-fn) => "start-2-end"))

(fact "sequences don't interfere with each other"
  (let [sequence-a-fn (sequence-fn :a #(str "a-" %))
        sequence-b-fn (sequence-fn :b #(str "b-" %))]
    (sequence-a-fn) => "a-1"
    (sequence-b-fn) => "b-1"))

(facts "fn-name"
  (fact "it should generate function name"
    (fn-name "test") => (symbol "sequence-test")))