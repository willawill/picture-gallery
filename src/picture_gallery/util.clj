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

(defn display-image [userid filename]
  [:a {:class name :href (image-uri userid name)}
    (image {:height "150px"}
     (image-uri userid filename))
    (if (= userid (session/get :user)) (check-box filename))])

