(ns stereotype-clj.stereotypes
  (:use
    [korma.db]
    [korma.core]
    [slingshot.slingshot :only [throw+]])
  (:require
   [stereotype-clj.entities :as entities]
   [stereotype-clj.sql :as sql]))

(defn arg-count [f]
  (let [m (first (.getDeclaredMethods (class f)))
     p (.getParameterTypes m)]
    (alength p)))

(defn- resolve-fn [value]
  (if (fn? value)
    (if (= (arg-count value) 0)
      (value)
      value)
    value))

(defn- resolve-parameterized-fn [attributes value]
  (if (fn? value)
    (when (> (arg-count value) 0) (value attributes))
    value))

(defn- resolve-values [attributes]
  (let [resolved (map resolve-fn (vals attributes))
        resolved (map #(resolve-parameterized-fn (zipmap (keys attributes) resolved) %) resolved)]
    (zipmap (keys attributes) resolved)))

(defn- fn-name [stereotype-id]
  (symbol (str "stereotype-" (name stereotype-id))))

(defn define [identifier attributes]
  (let [stereotype-id (entities/id-for identifier)]
    `(defn ~(fn-name stereotype-id) [& [overiding_attributes#]] (merge  ~attributes overiding_attributes#))))

(defn- attributes-for [stereotype-id overiding_attributes]
  (let [stereotype-method (resolve (fn-name stereotype-id))]
    (when-not stereotype-method
      (throw+ {:type ::undefined-stereotype
               :stereotype stereotype-id}))
    (stereotype-method overiding_attributes)))

(defn build [identifier & [overiding_attributes]]
  (let [stereotype-id (entities/id-for identifier)]
    (let [attributes (attributes-for stereotype-id overiding_attributes)
          resolved-attributes (resolve-values attributes)]
      resolved-attributes)))

(defn- map-insertions-to-keys [attributes]
  (into {}
    (map sql/replace-inserts-as-foreign-keys attributes)))

(defn- merge-with-meta [map-1 map-2]
  (let [meta-data (merge (meta map-1) (meta map-2))]
    (with-meta (merge map-1 map-2) meta-data)))

(defn build-and-insert [identifier & [overiding_attributes]]
  (let [attributes (->> overiding_attributes (build identifier) map-insertions-to-keys)
        insertion-result (insert (entities/entity-for identifier) (values attributes))]
    (merge-with-meta attributes (sql/pk insertion-result))))