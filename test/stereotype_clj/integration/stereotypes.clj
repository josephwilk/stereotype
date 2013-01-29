(ns stereotype-clj.integration.stereotypes
  (:require
    [stereotype-clj.core :refer :all]
    [clj-time.core       :refer :all]))

(defstereotype :users {:username "josephwilk"
                       :date_of_birth (fn [] (plus (now) (years 24)))
                       :company "monkeys"})