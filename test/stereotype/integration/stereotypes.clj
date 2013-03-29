(ns stereotype.integration.stereotypes
  (:use [stereotype.core]
        [stereotype.integration.support])
  (:require
    [clj-time.core       :as time]))

(defn init []
  (defsequence :email #(str "joe" % "@test.com"))

  (defstereotype :admin-users {:username "josephwilk"
                               :date_of_birth #(time/now)
                               :email #(generate :email)
                               :company "monkeys"
                               :urn (fn [user] (str (:company user) (:username user)))})

  (defstereotype address {:postcode "1234"})

  (defstereotype users {:name "josephwilk"
                        :address #(stereotype! address)}))