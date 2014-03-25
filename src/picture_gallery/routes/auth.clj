(ns picture-gallery.routes.auth
  (:require [compojure.core :refer :all]
            [picture-gallery.routes.home :refer :all]
            [picture-gallery.models.db :as db]
            [noir.util.crypt :as crypt]
            [picture-gallery.views.layout :as layout]
            [picture-gallery.routes.upload :refer [gallery-path]]
            [hiccup.form :refer :all]
            [noir.session :as session]
            [noir.response :as resp]
            [noir.validation :as vali])
  (:import java.io.File))


(defn valid? [id pass pass1]
  (vali/rule (vali/has-value? id) [:id "Id can not be empty"])
  (vali/rule (vali/has-value? pass) [:pass "Password can not be empty"])

  (vali/rule (vali/min-length? pass 5) [:pass "Password must be longer than 5"])

  (vali/rule (= pass pass1 ) [:pass "Passwords must match"])
  (not (vali/errors? :id :pass :pass1)))

(defn create-gallery-path []
  (let [user-path (File. (gallery-path))]
    (if-not (.exists user-path) (.mkdirs user-path))
    (str (.getAbsolutePath user-path) File/separator)))


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
            (password-field {:tabindex 2}"pass"))

            (form-item :pass1
            (label "pass1" "Comfirmation")
            (password-field {:tabindex 3}"pass1"))

            (submit-button {:tabindex 4}"Register"))))

(defn login-page []
  (layout/base
   (form-to [:post "/login"]
            (form-item :id
                       (label "user-id" "User id")
                       (text-field {:tabindex 1 :placehoder "User id"} "id"))
            (form-item :pass
                      (label "pass" "Password")
                      (password-field {:tabindex 2 :placehoder "Password"}"pass"))
            (submit-button {:tabindex 3} "Login"))))


(defn handle-login [id pass]
 (let [user (db/get-user id)]
   (if (and user (crypt/compare pass (:pass user)))
     (do
       (session/put! :user id)
       (resp/redirect "/"))
      (resp/redirect "/login"))))


(defn error-mapping [id ex]
  (cond
    (and (instance? org.postgresql.util.PSQLException ex)
      (= 0 (.getErrorCode ex)))
    (str "The user with id " id " already exists!")
    :else
    "An error has occured while processing the request"))

(defn handle-registration [id pass pass1]
  (if (valid? id pass pass1)
    (try

      (db/create-user {:id id :pass (crypt/encrypt pass)})
      (session/put! :user id)
      (create-gallery-path)
      (resp/redirect "/")
      (catch Exception ex
        (vali/rule false [:id (error-mapping id ex)])
        (registration-page)))
    (registration-page id)))

(defn handle-logout []
  (session/clear!)
  (resp/redirect "/")
)

(defroutes auth-routes
  (GET "/register" []
       (registration-page))

  (POST "/register" [id pass pass1]
        (handle-registration id pass pass1))

  (GET "/login" []
       (login-page))

  (POST "/login" [id pass]
        (handle-login id pass))

  (GET "/logout" []
       (handle-logout)))
