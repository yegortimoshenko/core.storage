(defproject com.yegortimoshenko/core.storage "20170421.143325"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.229"]
                 [com.cognitect/transit-cljs "0.8.239"]]
  :plugins [[lein-figwheel "0.5.9"]
            [lein-stamp "20170312.223701"]]
  :repl-options {:init-ns yegortimoshenko.core.storage}
  :cljsbuild {:builds
              [{:id "development"
                :source-paths ["src"]
                :figwheel true
                :compiler {:asset-path "scripts"
                           :main yegortimoshenko.core.storage
                           :output-dir "resources/public/scripts"
                           :output-to "resources/public/script.js"
                           :source-map true}}]})
