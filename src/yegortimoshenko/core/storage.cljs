(ns yegortimoshenko.core.storage
  (:require [cognitect.transit :as t])
  (:import goog.storage.Storage goog.storage.mechanism.HTML5LocalStorage))

(def read-handlers (atom {}))

(defn read-string [s]
  (t/read (t/reader :json {:handlers @read-handlers}) s))

(def write-handlers (atom {}))

(defn write-string [v]
  (t/write (t/writer :json {:handlers @write-handlers}) v))

(def ^:private storage
  (Storage. (HTML5LocalStorage.)))

(defn deposit [k v]
  (.set storage k (write-string v)))

(defn withdraw [k]
  (try (read-string (.get storage k))
       (catch js/SyntaxError _)))
