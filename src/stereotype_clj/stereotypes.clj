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

(defn- evaluate-values [map-to-eval]
  (into {} (for [[key-name value] map-to-eval] [key-name (
    let [evaled-value (eval value)]

    (if (fn? evaled-value)
      (cond
        (> (arg-count evaled-value) 0) (evaled-value {})
        :else (evaled-value))
      evaled-value))])))

(defn- fn-name [stereotype-id]
  (symbol (str "_stereotype-" (name stereotype-id))))

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
          evald-attributes (evaluate-values attributes)]
      evald-attributes)))

(defn- map-insertions-to-keys [attributes]
  (into {}
    (map sql/replace-inserts-as-foreign-keys attributes)))

(defn build-and-insert [identifier & [overiding_attributes]]
  (let [attributes (->> overiding_attributes (build identifier) map-insertions-to-keys)
        insertion-result (insert (entities/entity-for identifier) (values attributes))]
    (merge attributes (sql/pk insertion-result))))