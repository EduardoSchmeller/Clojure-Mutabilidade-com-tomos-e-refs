(ns hospital.aula3
  (:use [clojure.pprint])
  (:require [hospital.logic :as h.logic]
            [hospital.model :as h.model]))


(defn test-atomao []
  (let [hospital-silveira (atom {:espera h.model/fila_vazia})]
    (println hospital-silveira)
    (pprint hospital-silveira)
    (println @hospital-silveira)

   ; não é assim que altera um conteudo dentro de um átomo!
    (pprint (assoc @hospital-silveira :laboratorio1 h.model/fila_vazia))
    (pprint @hospital-silveira)

    ; Uma das maneiras de alteradas dados de um atomo.
    (swap! hospital-silveira assoc :laboratorio1 h.model/fila_vazia)
    (pprint hospital-silveira)

    (swap! hospital-silveira assoc :laboratorio2 h.model/fila_vazia)
    (pprint hospital-silveira)

    (swap! hospital-silveira update :laboratorio1 conj "111")
    (pprint hospital-silveira)))




;(test-atomao)


(defn chega-em-malvadao!
  [hospital pessoa]
  (swap! hospital h.logic/chega-em-pausado-logando hospital :espera pessoa)
  (println "Apos iniciar" pessoa))



(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-em-malvadao! hospital "111"))))
    (.start (Thread. (fn [] (chega-em-malvadao! hospital"222"))))
    (.start (Thread. (fn [] (chega-em-malvadao! hospital"333"))))
    (.start (Thread. (fn [] (chega-em-malvadao! hospital"444"))))
    (.start (Thread. (fn [] (chega-em-malvadao! hospital"555"))))
    (.start (Thread. (fn [] (chega-em-malvadao! hospital"666"))))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

(simula-um-dia-em-paralelo)

(range 100000)