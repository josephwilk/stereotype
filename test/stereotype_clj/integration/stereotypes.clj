(ns stereotype-clj.integration.stereotypes
  (:require
    [stereotype-clj.core :refer :all]
    [clj-time.core       :refer :all]))

(defstereotype :users {:username "josephwilk"
                       :date_of_birth #(clj-time.core/now)
                       :company "monkeys"})