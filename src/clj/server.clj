(ns server
  (:require
   [bidi.ring :as bidi]
   [hiccup.core :refer [html]]
   [ring.middleware.content-type :refer [wrap-content-type]]
   [ring.middleware.default-charset :refer [wrap-default-charset]]
   [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
   [ring.middleware.reload :refer [wrap-reload]]
   [ring.middleware.resource :refer [wrap-resource]]
   [ring.util.http-response :refer [ok header resource-response] :as resp]))

(defn- content-type [cnt ctype]
  (let [c (get {:html    "text/html; charset=UTF-8"
                :css     "text/css; charset=UTF-8"
                :js      "application/javascript; charset=UTF-8"
                :json    "application/json; charset=UTF-8"
                :transit "application/transit+json; charset=UTF-8"
                :font    "font/opentype"
                :svg     "image/svg+xml"} ctype
               (name ctype))]
    (resp/content-type cnt c)))

(defn- not-found [req]
  (-> (html [:h1 "Rats! We don't have it!"]) (resp/not-found) (content-type :html)))

(defn index-page []
  (html
    [:html
     [:title "Silly example"]
     [:head
      [:meta {:charset "UTF-8"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]]
     [:body
      [:div {:id "app"}]
      [:h1 "The mighty header"]
      [:script {:src "/env/dev.js"}]
      [:script "app.init"]]]))

(defn serve-index [_]
  (->
    (index-page)
    ok
    (content-type :html)))

(defn routes []
  "This data structure represents bidi-style routes.
   Handlers are either a ring handler function, or a keyword."
  ["/"
   [["" :index]
    [true not-found]]])

(defn- spa-handler
  [handler]
  (if (keyword? handler) serve-index handler))

(defn make-app-handler []
  (let [handler (-> (routes)
                    (bidi/make-handler spa-handler)
                    (wrap-resource "")
                    (wrap-defaults api-defaults)
                    (wrap-content-type)
                    (wrap-default-charset "utf-8")
                    (wrap-reload))]
    handler))
