(ns picture-gallery.routes.auth
  (:require [compojure.core :refer :all]
            [picture-gallery.routes.home :refer :all]
            [picture-gallery.views.layout :as layout]
            [hiccup.form :refer :all]
            [noir.session :as session]
            [noir.response :as resp]
            [noir.validation :as vali]))


(defn valid? [id pass pass1]
  (vali/rule (vali/has-value? id) [:id "Id can not be empty"])
  (vali/rule (vali/has-value? pass) [:pass "Password can not be empty"])

  (vali/rule (vali/min-length? pass 5) [:pass "Password must be longer than 5"])

  (vali/rule (= pass pass1 ) [:pass "Passwords must match"])
  (not (vali/errors? :id :pass :pass1)))

(defn format-error [[error]]
  [:div.error error])

(defn form-item [name label field]
  (list
   (vali/on-error name format-error)
   label
   field
   [:br]))

(defn registration-page [& [id]]
  (layout/base
   (form-to [:post "/register"]
            (form-item :id
            (label"user-id" "User id")
            (text-field {:tabindex 1}"id" id))

            (form-item :pass
            (label "pass" "Password")
            (password-field {:tabindex "2"}"pass"))

            (form-item :pass1
            (label "pass1" "Comfirmation")
            (password-field {:tableindex "3"}"pass1"))

            (submit-button {:tabindex 4}"Register"))))

(defn handle-registration [id pass pass1]
  (if (valid? id pass pass1)
    (do   (session/put! :user id)
          (resp/redirect "/"))
    (registration-page id)))


(defroutes auth-routes
  (GET "/register" []
       (registration-page))

  (POST "/register" [id pass pass1]
        (handle-registration id pass pass1)))

