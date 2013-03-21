(ns stereotype-clj.sql)

(def inserted-id-key
  (keyword "__inserted_key_id__"))

(defn- inserted-id-key-from-db [insertion-result]
  (or
   (insertion-result (keyword "last_insert_rowid()"))
   (insertion-result :generated_key)))

(defn- extract-key [attributes]
  (attributes inserted-id-key))

(defn insertion? [value]
  (and
   (map? value)
   (contains? value inserted-id-key)))

;Note: assumes pk is id, this may not be the case
(defn pk [insertion-result]
  (let [inserted-id (inserted-id-key-from-db insertion-result)]
    {:id inserted-id
     inserted-id-key inserted-id}))

;Assumes name of association maps to foreign key :address maps to :address_id
(defn fk [key-name attributes]
  (let [foreign-key-name  (str (name key-name) "_id")
        foreign-key-value (extract-key attributes)]
    [foreign-key-name foreign-key-value]))