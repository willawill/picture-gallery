(ns picture-gallery.routes.home
  (:require [compojure.core :refer :all]
            [noir.session :as session]
            [picture-gallery.views.layout :as layout]
            [picture-gallery.models.db :as db]))


(defn get-user []
  (session/get :user))

(defn home []
  (layout/common [:h1 "Hello " (get-user)]))

(defroutes home-routes
  (GET "/" [] (home)))
