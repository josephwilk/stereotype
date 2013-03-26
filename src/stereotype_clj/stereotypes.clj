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

(defn- resolve-value [value]
  (if (fn? value)
    (cond
      (> (arg-count value) 0) (value {})
      :else (value))
    value))

(defn- resolve-values [attributes]
  (zipmap (keys attributes) (map resolve-value (vals attributes))))

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

(defn build-and-insert [identifier & [overiding_attributes]]
  (let [attributes (->> overiding_attributes (build identifier) map-insertions-to-keys)
        insertion-result (insert (entities/entity-for identifier) (values attributes))]
    (merge attributes (sql/pk insertion-result))))