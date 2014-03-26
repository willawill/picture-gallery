(ns picture-gallery.util
  (:require [hiccup.util :refer [url-encode]]
            [hiccup.element :refer [image]])
  (:import java.io.File))

(def galleries "galleries")



(defn image-uri [userid filename]
  (str "/img/" userid File/separator filename))

(defn display-image [userid filename]
  (image {:height "150px"}
   (image-uri userid filename)))

