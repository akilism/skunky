(defproject super-skunk "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [cheshire "5.6.1"]
                 [fipp "0.6.5"]
                 [clj-http "3.0.1"]]
  :main ^:skip-aot super-skunk.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
