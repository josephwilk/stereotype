(ns stereotype-clj.integration.stereotypes
  (:require
    [stereotype-clj.core :refer :all]
    [clj-time.core       :refer :all]))

(defstereotype :users {:username "josephwilk"
                       :date_of_birth #(clj-time.core/plus (clj-time.core/now) (clj-time.core/years 24))
                       :company "monkeys"})