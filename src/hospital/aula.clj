(ns hospital.aula
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]
             [hospital.logic :as h.logic]))

(defn simula-um-dia []
  "root binding"
  (def hospital (hospital.model/novo-hospital))
  (def hospital (h.logic/chega-em hospital :espera "111"))
  (def hospital (h.logic/chega-em hospital :espera "222"))
  (def hospital (h.logic/chega-em hospital :espera "333"))
  (clojure.pprint/pprint hospital)



(def hospital (h.logic/chega-em hospital :laboratorio1 "444"))
  (def hospital (h.logic/chega-em hospital :laboratorio3"555"))
  (clojure.pprint/pprint hospital)

  (def hospital (h.logic/atende hospital :laboratorio1))
  (def hospital (h.logic/atende hospital :espera))
  (clojure.pprint/pprint hospital)

  (def hospital (h.logic/chega-em hospital :espera "666"))
  (def hospital (h.logic/chega-em hospital :espera "777"))
  (def hospital (h.logic/chega-em hospital :espera "888"))
  (pprint hospital)
  (def hospital (h.logic/chega-em hospital :espera "999"))
  (clojure.pprint/pprint hospital))

;(pprint hospital)

;(simula-um-dia)
(defn chega-em-malvadao
  [pessoa]
  (def hospital (h.logic/chega-em-pausado hospital :espera pessoa))
  (println "Apos iniciar" pessoa))


;; Muitom claro problema Therad.
(defn simula-um-dia-em-paralelo
  []
  (def hospital (h.model/novo-hospital))
  (.start(Thread. (fn [] (chega-em-malvadao "111"))))
  (.start(Thread. (fn [] (chega-em-malvadao "222"))))
  (.start(Thread. (fn [] (chega-em-malvadao "333"))))
  (.start(Thread. (fn [] (chega-em-malvadao "444"))))
  (.start(Thread. (fn [] (chega-em-malvadao "555"))))
  (.start(Thread. (fn [] (chega-em-malvadao "666"))))
  (.start(Thread. (fn [] (Thread/sleep 4000)
                          (pprint hospital)))))



(simula-um-dia-em-paralelo)


























