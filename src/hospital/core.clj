(ns hospital.core
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]))


(let [hospital-do-edu (h.model/novo-hospital)]
  (pprint hospital-do-edu))


