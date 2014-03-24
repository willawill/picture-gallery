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

(defn common [& content]
  (base
   (if-let [user (session/get :user)]
     [:p user]
     [:div(link-to "/register" "Register") [:pre]
      (link-to "/login" "Login")])
   content))




