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

(defn add-image [userid filename]
  (with-db
    sql/transaction
    (if (sql/with-query-results
          result
            ["select * from images where userid=? and filename=?" userid filename]
            (empty? result))
        (sql/insert-record :images {:userid userid :filename filename})
        (throw
           (Exception. "Duplicated")))))
