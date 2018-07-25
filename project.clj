(defproject social-analysis "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :plugins [[lein-cljsbuild "0.3.2"]
            [lein-codox "0.10.3"]]

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.json "0.2.2"]
                 [me.raynes/fs "1.4.4"]
                 [org.clojure/clojurescript "1.10.339"]]
 :cljsbuild {:builds [{:source-paths ["src-cljs"],
              :compiler {:pretty-printer true,
              :output-to "www/js/main.js",
              :optimizations :whitespace}}]}

  :main ^:skip-aot social-analysis.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
