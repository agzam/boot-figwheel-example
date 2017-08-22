(set-env!
  :dependencies
  '[[adzerk/boot-cljs "2.0.0" :exclusions [org.clojure/clojurescript]]
    [adzerk/boot-cljs-repl "0.4.0-SNAPSHOT" :scope "test"]
    [adzerk/boot-reload "0.5.2" :scope "test"]
    [bidi "2.0.17"]
    #_[clojure-future-spec "1.9.0-alpha17"]
    [com.cemerick/piggieback "0.2.1" :scope "test"]
    [environ "1.1.0"]
    [hiccup "1.0.5" :exclusions [cljsjs/react]]
    [metosin/ring-http-response "0.8.0"]
    [org.clojure/clojure "1.9.0-alpha17"]
    [org.clojure/clojurescript "1.9.908"]
    [org.clojure/tools.nrepl "0.2.12" :scope "test"]
    [pandeiro/boot-http "0.8.3" :scope "test"]
    [re-frame "0.9.4"]
    [reagent "0.6.2"]
    [ring "1.5.0"]
    [ring-middleware-format "0.7.0"]
    [ring/ring-defaults "0.3.0-beta1"]
    [weasel "0.7.0" :scope "test"]]
   :resource-paths #{"resources/public"}
   :source-paths #{"src/clj" "src/cljs"})

  (require
    '[adzerk.boot-cljs :refer [cljs]]
    '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
    '[adzerk.boot-reload :refer [reload]]
    '[sample.server]
    '[pandeiro.boot-http :refer [serve]])

  (task-options!
    repl {:middleware '[cemerick.piggieback/wrap-cljs-repl]}
    cljs-repl {:nrepl-opts {:port 9009}})

  (deftask dev
    []
    (comp
      (watch)
      (serve :handler 'sample.server/app-handler
             :port 3000)
      (reload :on-jsload 'sample.app/init)
      (cljs :ids #{"main"}
            :optimizations :none
            :compiler-options {:asset-path           "main.out"
                               :verbose              true
                               :parallel-build       :true
                               :recompile-dependents false})))
