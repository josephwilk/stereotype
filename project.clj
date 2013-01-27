(defproject stereotype-clj "0.1.0-SNAPSHOT"
  :description "A library for setting up test data in Clojure "
  :url "https://github.com/josephwilk/stereotype-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [korma "0.3.0-RC2"]]

  :min-lein-version "2.0.0"
  :profiles {:dev {:dependencies [[midje "1.4.0"]
                                  [bultitude "0.1.7"]
                                  [org.clojure/java.jdbc "0.2.3"]
                                  [org.xerial/sqlite-jdbc "3.7.2"]]
                   :plugins      [[lein-midje "2.0.4"]]}})
