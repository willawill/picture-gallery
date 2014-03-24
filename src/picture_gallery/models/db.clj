(ns picture-gallery.models.db
  (:require [clojure.java.jdbc :as sql]))


(def db
  {:subprotocol "postgresql"
   :subname "//localhost/gallery"
   :user "admin"
   :password "admin"
   })


(defmacro with-db [f & body]
  `(sql/with-connection ~db (~f ~@body)))

(defn create-user-table []
  (sql/with-connection db
   (sql/create-table
    :users
    [:id "varchar(32) PRIMARY KEY"]
    [:pass "varchar(100)"]
     )))

(defn drop-table [name]
  (sql/with-connection db
    (sql/drop-table name)))

(defn create-user [user]
  (sql/with-connection db
    (sql/insert-record :users user)))

(defn get-user [id]
  (with-db sql/with-query-results
       result
         ["select * from users where id = ?" id]
                       (first result)))

