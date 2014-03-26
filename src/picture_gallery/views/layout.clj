(ns picture-gallery.views.layout
  (:require [hiccup.page :refer [html5 include-css]]
            [hiccup.element :refer [link-to]]
            [noir.session :as session]))

(defn base [& content]
  (html5
    [:head
     [:title "Welcome to picture-gallery"]
     (include-css "/css/screen.css")]
    [:body content]))

(defn guest-menu []
  (list
   [:div (link-to "/register" "Register")]
   [:div (link-to "/login" "Login")]))

(defn user-menu [user]
  (list
   [:div (link-to "/" "My gallery")]
   [:div (link-to "/upload" "Upload")]
   [:div (link-to "/logout" "Logout")]))

(defn common [& content]
  (base
   (if-let [user (session/get :user)]
      (user-menu user)
     (guest-menu))
   content))




