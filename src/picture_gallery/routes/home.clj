(ns picture-gallery.routes.home
  (:require [compojure.core :refer :all]
            [picture-gallery.views.layout :as layout]
            [picture-gallery.util :refer :all]
            [picture-gallery.routes.gallery :refer [user-gallery]]))



(defn home []
  (layout/common
     [:h1 "Hello " (get-user)]
     (user-gallery (get-user))))

(defroutes home-routes
  (GET "/" [] (home)))
