(ns social_analysis.ego
  (:require [clojure.java.io :as io]
            [clojure.set :as set]
            [clojure.string :as string]
            [clojure.data.json :as json]
            [clojure.core.reducers :as r]
            [social_analysis.graph :as g]
            [social_analysis.util :as u]
            me.raynes.fs :as fs)
  (:import [java.io File]))


(defn read-edge-file
  "reads and parses .edges file contents"
  [filename]
  (with-open [f (io/reader filename)]
    (->>
      f
      line-seq
      (r/map #(string/split % #"\s+"))
      (r/map #(mapv (fn [x] (Long/parseLong x)) %))
      (r/reduce #(g/add %1 (first %2) (second %2))
              g/empty-graph))))

(defn read-edge-files
  "locates and reads file"
  [ego-dir]
  (r/reduce g/merge-graphs {}
    (r/map read-edge-file
      (fs/find-files ego-dir #".*\.edges$"))))
