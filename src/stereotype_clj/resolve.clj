(ns stereotype-clj.resolve)

(defn- arg-count [f]
  (let [m (first (.getDeclaredMethods (class f)))
     p (.getParameterTypes m)]
    (alength p)))

(defn- resolve-unparameterized-fn [value]
  (if (fn? value)
    (if (= (arg-count value) 0)
      (value)
      value)
    value))

(defn- resolve-parameterized-fn [attributes value]
  (if (fn? value)
    (when (> (arg-count value) 0) (value attributes))
    value))

(defn- resolve-unparameterized-fns [values]
  (map resolve-unparameterized-fn values))

(defn- resolve-parameterized-fns [values keys]
  (map (partial resolve-parameterized-fn (zipmap keys values)) values))

(defn- resolve-vals [values keys]
  (-> values
      resolve-unparameterized-fns
      (resolve-parameterized-fns keys)))

(defn all [attributes]
  (let [attribute-keys (keys attributes)
        attribute-vals (vals attributes)]
    (zipmap attribute-keys
            (resolve-vals attribute-vals attribute-keys))))