(ns picture-gallery.models.db
  (:require [clojure.java.jdbc :as sql]))

(def db
  {:subprotocol "postgresql"
   :subname "//localhost/gallery"
   :user "admin"
   :password "admin"
   })


(defn create-user-table []
  (sql/with-connection db
   (sql/create-table
    :users
    [:id "varchar (32) PRIMARY KEY"]
    [:pass "varchar (32)"]
     )))

