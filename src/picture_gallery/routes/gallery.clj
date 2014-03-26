(ns picture-gallery.routes.gallery
  (:require [compojure.core :refer :all]
            [hiccup.element :refer :all]
            [picture-gallery.models.db :as db]
            [picture-gallery.views.layout :as layout]
            [picture-gallery.util :refer :all]
            [noir.session :as session]
            [noir.response :as resp]
            ))


(defn user-gallery [user-id]
  (layout/base
    (map (fn [file] (display-image user-id (:filename file))) (db/get-images user-id))))

(defroutes gallery-routes
  (GET "/gallery/:user-id" [user-id] (user-gallery user-id)))