(ns stereotype-clj.integration.stereotypes
  (:use
    [stereotype-clj.core])
  (:require
    [clj-time.core       :as time]))

(defsequence :email #(str "joe" % "@test.com"))

(defstereotype :users {:username "josephwilk"
                       :date_of_birth #(time/now)
                       :email #(generate :email)
                       :company "monkeys"
                       :urn (fn [user] (str (:company user) (:username user)))})
