(ns picture-gallery.routes.gallery
  (:require [compojure.core :refer :all]
            [hiccup.element :refer :all]
            [picture-gallery.models.db :as db]
            [picture-gallery.views.layout :as layout]
            [noir.session :as session]
            [noir.response :as resp]
            ))

(defn image-uri [userid filename]
  (str "/img/" userid "/" filename))


(defn display-image [userid filename]
  (image
   (image-uri userid filename)))

(defn galleries [user-id]
  (layout/base
    (map (fn [file] (display-image user-id (:filename file))) (db/get-images user-id))))

(defroutes gallery-routes
  (GET "/gallery/:user-id" [user-id] (galleries user-id)))