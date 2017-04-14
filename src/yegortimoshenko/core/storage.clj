(ns yegortimoshenko.core.storage
  (:refer-clojure :exclude [load])
  (:import [java.io ByteArrayInputStream ByteArrayOutputStream ObjectInputStream ObjectOutputStream IOException]
           [java.util.prefs Preferences]))

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
(def ^:dynamic *key* (str ::storage))

(defn ^:private preferences ^Preferences [node]
  (-> (Preferences/userRoot) (.node node)))

(def ^:private preferences-memo (memoize preferences))

(defn save [k v]
  (.putByteArray (preferences-memo k) *key* (write-bytes v)))

(defn load [k]
  (try (read-bytes (.getByteArray (preferences-memo k) *key* *default*))
       (catch IOException e nil)))
