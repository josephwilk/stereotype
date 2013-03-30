(ns stereotype.stereotypes
  (:use
    [korma.db]
    [korma.core]
    [slingshot.slingshot    :only [throw+]])
  (:require
   [stereotype.entities :as entities]
   [stereotype.sql      :as sql]
   [stereotype.resolve  :as resolve]))

(defn- fn-name [stereotype-id]
  (symbol (str "stereotype-" (name stereotype-id))))

(defn define [identifier attributes]
  (let [stereotype-id (entities/id-for identifier)]
    `(defn ~(fn-name stereotype-id) [& [overiding-attributes#]] (merge  ~attributes overiding-attributes#))))

(defn- attributes-for [stereotype-id overiding-attributes]
  (let [stereotype-fn (resolve (fn-name stereotype-id))]
    (when-not stereotype-fn
      (throw+ {:type ::undefined-stereotype
               :stereotype stereotype-id}))
    (stereotype-fn overiding-attributes)))

(defn build [identifier & [overiding-attributes]]
  (let [stereotype-id (entities/id-for identifier)
        attributes (attributes-for stereotype-id overiding-attributes)]
    (resolve/all attributes)))

(defn- map-insertions-to-keys [attributes]
  (into {}
    (map sql/replace-inserts-as-foreign-keys attributes)))

(defn- merge-with-meta [map-1 map-2]
  (let [meta-data (merge (meta map-1) (meta map-2))]
    (with-meta (merge map-1 map-2) meta-data)))

(defn build-and-insert [identifier & [overiding-attributes]]
  (let [attributes (->> overiding-attributes (build identifier) map-insertions-to-keys)
        insertion-result (insert (entities/entity-for identifier) (values attributes))]
    (merge-with-meta attributes (sql/pk insertion-result))))
