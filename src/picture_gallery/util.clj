(ns picture-gallery.util
  (:require [hiccup.util :refer [url-encode]]
            [hiccup.element :refer [image]]
            [hiccup.form :refer [check-box]]
            [noir.util.route :refer [restricted]]
            [noir.session :as session])
  (:import java.io.File))

(def galleries "galleries")

(defn image-uri [userid filename]
  (str "/img/" userid File/separator filename))

(defn get-user []
  (session/get :user))


(defn login? [userid]
  (and userid (= userid (get-user))))

(defn display-image [userid filename]
  [:a {:class name :href (image-uri userid name)}
    (image {:height "150px"}
     (image-uri userid filename))
    (if (login? userid) (check-box filename))])

