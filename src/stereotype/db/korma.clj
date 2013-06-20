(ns stereotype.db.korma
  (:require
   [stereotype.db :refer :all]
   [korma.core :as korma]
   [korma.db :refer :all]))

(deftype Korma [db]
  StereotypeDb
  (insert [_ entity attributes]
    (korma/insert entity (korma/values attributes))))

(defn with [db]
  (Korma. db))
