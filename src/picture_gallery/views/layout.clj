(ns picture-gallery.views.layout
  (:require [hiccup.page :refer [html5 include-css include-js]]
            [hiccup.element :refer [link-to]]
            [compojure.response :refer [Renderable]]
            [ring.util.response :refer [content-type response]]
            [noir.session :as session]))

(defn utf-8-response [html]
  (content-type (response html) "text/html; charset=utf-8"))


(deftype RenderablePage [content]
  Renderable
  (render [this request]
    (utf-8-response
      (html5
        [:head
         [:title "Welcome to picture-gallery"]
         (include-css "/css/screen.css")
         [:script {:type "text/javascript"}
          (str "text/javascript context=\"" (:context request) "\";")]
         (include-js "//code.jquery.com/jquery-2.0.2.min.js")]
        [:body content]))))

(defn base [& content]
  (RenderablePage. content))


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




