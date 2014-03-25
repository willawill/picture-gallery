(ns picture-gallery.routes.upload
  (:require [compojure.core :refer [defroutes GET POST]]
            [hiccup.form :refer :all]
            [hiccup.element :refer [image]]
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

(defn upload-page [info]
  (layout/common
   [:h2 "Upload a picture"]
   [:p info]
   (form-to {:enctype "multipart/form-data"}
            [:post "/upload"]
            (file-upload :file)
            (submit-button "Upload"))))

(defn handle-upload [{:keys [filename] :as file}]
  (println file)
  (upload-page
   (if (empty? filename)
   "Please select a file to upload"
   "Success")
   ))

(defroutes upload-routes
  (GET "/upload" [info] (upload-page info))

  (POST "/upload" [file] (handle-upload file)))
