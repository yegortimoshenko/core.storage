(ns yegortimoshenko.core.storage
  (:refer-clojure :exclude [load])
  (:import (java.io ByteArrayInputStream ByteArrayOutputStream
                    ObjectInputStream ObjectOutputStream IOException)
           (java.util.prefs Preferences)))

(defn read-bytes ^Object [^bytes bs]
  (with-open [in (ByteArrayInputStream. bs)
              obj (ObjectInputStream. in)]
    (.readObject obj)))

(defn write-bytes ^bytes [^Object v]
  (with-open [out (ByteArrayOutputStream.)
              obj (ObjectOutputStream. out)]
    (.writeObject obj v)
    (.toByteArray out)))

(def ^:dynamic *default* (write-bytes nil))
(def ^:dynamic *node* "/com/yegortimoshenko/core/storage")

(defn ^:private preferences ^Preferences [node]
  (-> (Preferences/userRoot) (.node node)))

(defn save [k v]
  (.putByteArray (preferences *node*) (pr-str k) (write-bytes v)))

(defn load [k]
  (try (read-bytes (.getByteArray (preferences *node*) (pr-str k) *default*))
       (catch IOException _)))
