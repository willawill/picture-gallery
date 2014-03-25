(ns picture-gallery.routes.upload
  (:require [compojure.core :refer [defroutes GET POST]]
            [hiccup.form :refer :all]
            [hiccup.element :refer [image link-to]]
            [hiccup.util :refer [url-encode]]
            [noir.session :as session]
            [noir.response :as resp]
            [noir.util.route :refer [restricted]]
            [noir.io :refer [upload-file resource-path]]
            [picture-gallery.views.layout :as layout]
            [picture-gallery.models.db :as db]
            [clojure.java.io :as io]
            [ring.util.response :refer [file-response]] )
  (:import [java.io File FileInputStream FileOutputStream]
           [java.awt.image AffineTransformOp BufferedImage]
           java.awt.RenderingHints
           java.awt.geom.AffineTransform
           javax.imageio.ImageIO))

(def galleries "galleries")
(defn gallery-path [userid]
  (str galleries File/separator userid)
)

(defn image-uri [userid filename]
  (str "/img/" userid "/" filename))

(defn serve-file [user-id file-name]
  (file-response (str (gallery-path user-id) File/separator file-name)))


(defn upload-page [info]
   (layout/common
     [:h2 "Upload a picture"]
     [:p info]
     (form-to {:enctype "multipart/form-data"}
              [:post "/upload"]
              (file-upload :file)
              (submit-button "Upload"))))

(defn handle-upload [{:keys [filename] :as file}]
  (upload-page
   (if (empty? filename)
   "Please select a file to upload"

     (try
       (let [userid (session/get :user)]
         (noir.io/upload-file (gallery-path userid) file)

         (db/add-image userid filename)
         [:div(link-to (image-uri userid filename) "View")]
         (image
          (image-uri userid filename)))


       (catch Exception ex
         (str "error uploading image" ex))))))


(defroutes upload-routes
  (GET "/upload" [info] (restricted (upload-page info)))

  (POST "/upload" [file] (restricted (handle-upload file)))

  (GET "/img/:user-id/:file-name" [user-id file-name] (serve-file user-id file-name)))
