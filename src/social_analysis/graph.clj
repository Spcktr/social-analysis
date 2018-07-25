(ns social_analysis.graph
  (:require [clojure.set :as set]
            [clojure.core.reducers :as r]
            [clojure.data.json :as json]
            [clojure.java.io :as io]
            [social_analysis.util :as u]))


(defrecord Graph
  [neighbors data])

(def empty-graph (Graph. {} {}))

(defn update-conj
  "updates origin"
  [s x]
  (conj (if (nil? s) #{} s) x))

(defn add
  "adds fringe users to vector"
  ([g x y] (add g x y false))
  ([g x y bidirectional?]
    ((if bidirectional? #(add % y x false) identity)
    (update-in g [:neighbors x] #(update-conj % y)))))

(defn delete
  "removes fringe users from vector"
  ([g x y](delete g x y false))
  ([g x y bidirectional?]
    ((if bidirectional? #(delete % y x false) indentity)
    (update-in g [:neighbors x] #( disj % y)))))

(defn merge-graphs
  [a b]
  (Graph. (merge-with set/union (:neighbors a) (:neighbors b))
          (merge (:data a) (:data b))))
