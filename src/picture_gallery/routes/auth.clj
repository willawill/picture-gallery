(ns picture-gallery.routes.auth
  (:require [compojure.core :refer :all]
            [picture-gallery.routes.home :refer :all]
            [picture-gallery.views.layout :as layout]
            [hiccup.form :refer :all]
            [noir.session :as session]
            [noir.response :as resp]))

(defn registration-page [& [id]]
  (layout/common
   (form-to [:post "/register"]
            (label "user-id" "User id")
            (text-field "id" id)
            [:br]
            (label "pass" "Password")
            (password-field "pass")
            [:br]
            (label "pass1" "Comfirmation")
            (password-field "pass1")
            [:br]
            (submit-button "Register"))))

(defn handle-registration [id pass pass1]
  (session/put! :user id)
  (resp/redirect "/"))


(defroutes auth-routes
  (GET "/register" []
       (registration-page))

  (POST "/register" [id pass pass1]
        (handle-registration id pass pass1)))

