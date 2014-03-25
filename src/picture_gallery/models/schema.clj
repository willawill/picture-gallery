(ns picture-gallery.models.schema
  (:require [picture-gallery.models.db :refer :all]
            [clojure.java.jdbc :as sql]))


(defn create-user-table []
  (sql/with-connection db
   (sql/create-table
    :users
    [:id "varchar(32) PRIMARY KEY"]
    [:pass "varchar(100)"]
     )))

(defn create-image-table []
  (sql/with-connection db
     (sql/create-table
      :images
      [:userid "varchar(32)"]
      [:filename "varchar(200)"])))
