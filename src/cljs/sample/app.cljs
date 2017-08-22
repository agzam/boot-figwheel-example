(ns sample.app)

(enable-console-print!)

(js/console.log "to be or not to be")

(defn ^:export init []
  (js/console.log "It's alive!"))
