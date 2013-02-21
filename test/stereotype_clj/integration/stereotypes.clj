(ns stereotype-clj.integration.stereotypes
  (:require
    [stereotype-clj.core :refer :all]
    [clj-time.core       :refer :all]))

(defstereotype :users {:username "josephwilk"
                       :date_of_birth #(now)
                       :company "monkeys"
                       :urn (fn [user] (str (:company user) (:username user)))})