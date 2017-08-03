(set-env!
  :dependencies
  '[[adzerk/boot-cljs "2.0.0" :exclusions [org.clojure/clojurescript]]
    [ajchemist/boot-figwheel "0.5.4-6"]
    [bidi "2.0.17"]
    [clojure-future-spec "1.9.0-alpha17"]
    [com.cemerick/piggieback "0.2.1"]
    [environ "1.1.0"]
    [figwheel-sidecar "0.5.7" :scope "test"]
    [hiccup "1.0.5" :exclusions [cljsjs/react]]
    [metosin/ring-http-response "0.8.0"]
    [org.clojure/clojure "1.8.0"]
    [org.clojure/clojurescript "1.9.671"]
    [org.clojure/tools.nrepl "0.2.12"]
    [re-frame "0.9.4"]
    [reagent "0.6.2"]
    [ring "1.5.0"]
    [ring-middleware-format "0.7.0"]
    [ring/ring-defaults "0.3.0-beta1"]
    [weasel "0.7.0"]]
   :resource-paths #{"resources/public"}
   :source-paths #{"src/clj" "src/cljs"})

  (require
    '[boot-figwheel :refer :all :rename {cljs-repl fw-cljs-repl}]
    '[server :as server])

  (def all-builds
    [{:id           "dev"
      :source-paths ["src/clj" "src/cljs"]
      :compiler     {:main                 'app
                     :output-to            "env/dev.js"
                     :asset-path           "env"
                     :optimizations        :none
                     :verbose              true
                     :parallel-build       true
                     :source-map-timestamp true
                     :recompile-dependents false}
      :figwheel     {:build-id         "dev"
                     :heads-up-display true
                     :autoload         true}}])

  (def ring-handler
    (server/make-app-handler))

  (task-options!
    figwheel {:figwheel-options {:ring-handler 'boot.user/ring-handler
                                 :server-port  3333}
              :build-ids        ["dev"]
              :all-builds       all-builds})
