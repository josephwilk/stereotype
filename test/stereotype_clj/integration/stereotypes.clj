(ns stereotype-clj.integration.stereotypes
  (:require
    [stereotype-clj.core :refer :all]
    [clj-time.core       :as :time]))

(defstereotype :users {:username "josephwilk"
                       :date_of_birth #(time/now)
                       :company "monkeys"
                       :urn (fn [user] (str (:company user) (:username user)))})