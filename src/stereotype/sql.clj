(ns stereotype.sql)

(def inserted-id-key
  :inserted-id)

(defn- inserted-id-key-from-db [insertion-result]
  (or
   (insertion-result (keyword "last_insert_rowid()"))
   (insertion-result :generated_key)))

(defn- extract-key [attributes]
  (inserted-id-key (meta attributes)))

(defn- insertion? [value]
  (and
   (map? value)
   (contains? (meta value) inserted-id-key)))

;Assumes name of association maps to foreign key :address maps to :address_id
(defn- fk [key-name attributes]
  (let [foreign-key-name  (str (name key-name) "_id")
        foreign-key-value (extract-key attributes)]
    [foreign-key-name foreign-key-value]))

;Note: assumes pk is id, this may not be the case
(defn pk [insertion-result]
  (let [inserted-id (inserted-id-key-from-db insertion-result)]
    ^{inserted-id-key inserted-id} {:id inserted-id}))

(defn replace-inserts-as-foreign-keys [[attribute-name result]]
  (if (insertion? result)
    (fk attribute-name result)
    [attribute-name result]))