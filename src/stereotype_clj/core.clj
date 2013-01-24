(ns stereotype-clj.core)

(def sterotypes (atom {}))

(defn update-sterotypes [new-sterotype]
  (reset! sterotypes (merge @sterotypes new-sterotype)))

(defmacro defsterotype
  "define a sterotype with default attributes"
  [name attributes]
  (update-sterotypes {name attributes})
  (println @sterotypes))

(defn sterotype
  "returns the sterotype defaults"
  [name & [overiding_attributes]]
  (merge (name @sterotypes) overiding_attributes))