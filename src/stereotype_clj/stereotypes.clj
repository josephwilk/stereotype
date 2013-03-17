(ns stereotype-clj.stereotypes
  (:use
    [korma.db]
    [korma.core]
    [slingshot.slingshot :only [throw+]])
  (:require
    [stereotype-clj.entities :as entities]))

(def stereotypes (atom {}))

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

(defn reset-stereotypes []
  (reset! stereotypes {}))

(defn update-stereotypes [new-stereotype]
  (swap! stereotypes merge new-stereotype))

(defn define [identifier attributes]
  (let [stereotype-id (entities/id-for identifier)]
    (update-stereotypes {stereotype-id attributes})))

(defn build [identifier & [overiding_attributes]]
  (let [stereotype-id (entities/id-for identifier)]
    (when-not (contains? @stereotypes stereotype-id)
      (throw+ {:type ::undefined-stereotype
               :stereotype stereotype-id
               :stereotypes-defined (vec (keys @stereotypes)))}))
    (let [attributes (merge (@stereotypes stereotype-id) overiding_attributes)
          evald-attributes (evaluate-values attributes)]
      evald-attributes))

(defn build-and-insert [identifier & [overiding_attributes]]
  (let [attributes (build identifier overiding_attributes)
        insert-details (insert (entities/entity-for identifier) (values attributes))]
    attributes))