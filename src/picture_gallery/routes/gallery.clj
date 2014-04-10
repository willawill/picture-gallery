(ns picture-gallery.routes.gallery
  (:require [compojure.core :refer :all]
            [hiccup.element :refer :all]
            [hiccup.page]
            [picture-gallery.models.db :as db]
            [picture-gallery.views.layout :as layout]
            [picture-gallery.util :refer :all]
            [noir.util.route :refer [restricted]]

            [noir.session :as session]
            [noir.response :as resp]
            [hiccup.page :refer :all]
            ))


(defn user-gallery [user-id]
  (layout/base
    [:div (map (fn [file] (display-image user-id (:filename file))) (db/get-images user-id))
    (if (and user-id (= user-id (session/get :user)))
      [:input#delete {:type "submit" :value "delete-images"}])]))


(defroutes gallery-routes
  (GET "/gallery/:user-id" [user-id] (restricted (user-gallery user-id))))
