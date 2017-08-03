(ns app)

(enable-console-print!)

(js/console.log "test")

(defn ^:export init []
  (js/console.log "It's alive!"))
