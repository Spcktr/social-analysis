(ns social_analysis.graph
  (:require [clojure.set :as set]
            [clojure.core.reducers :as r]
            [clojure.data.json :as json]
            [clojure.java.io :as io]))


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
    ((if bidirectional? #(delete % y x false) identity)
    (update-in g [:neighbors x] #( disj % y)))))

(defn merge-graphs
  [a b]
  (Graph. (merge-with set/union (:neighbors a) (:neighbors b))
          (merge (:data a) (:data b))))

(defn get-value
  "getting vertex number for hashmap"
  ([g x] ((:data g) x))
  ([g x k] ((get-value g x) k)))

(defn set-value
  "setting vertex number"
  ([g x v] (assoc-in g [:data x] v))
  ([g x k v] (set-value g x (assoc (get-value g x) k v))))

(defn get-vertices
  "gets neighbors and check bidirectional relationship a -> b"
  [graph]
  (reduce set/union (set (keys (:neighbors graph)))
  (vals (:neighbors graph))))

(defn get-edges
  [graph]
  (let [pair-edges (fn [[v neighbors]]
    (map #(vector v %) neighbors))]
    (mapcat pair-edges (:neighbors graph))))

(defn bf-seq
  "sets lazy sequence for checking vertecies"
  ([get-neighbors a]
    (bf-seq
      get-neighbors
      (conj clojure.lang.PersistentQueue/EMPTY [a])
      #{a}))
    ([get-neighbors q seen])
      (lazy-seq
        (when-not (empty? q)
        (let [current (first q)
        nbors (remove seen (get-neighbors (last current)))]
          (cons current
            (bf-seq get-neighbors
              (into (pop q)
                (map #(conj current %) nbors))
              (into seen nbors)))))))
(defn breadth-first
  [graph a]
  (bf-seq (:neighbors graph) a))

(defn bfs
  "breath first search for functionalities(filters)"
  [graph a b]
  (frist (filter #(= (last %) b) (breadth-first graph a))))
