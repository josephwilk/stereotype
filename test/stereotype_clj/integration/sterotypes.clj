(ns stereotype-clj.integration.sterotypes
  (:require
    [stereotype-clj.core :refer :all]
    [clj-time.core       :refer :all]))

(defsterotype :users {:username "josephwilk"
                      :date_of_birth (fn [] (plus (now) (years 24)))
                      :company "monkeys"})