(ns hospital.aula4
  (:use [clojure pprint])
  (:require [hospital.logic :as h.logic]
            [hospital.model :as h.model]))

(defn chega-sem-malvado! [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa)
  (println "apos inserir" pessoa))

#_(defn simula-um-dia-em-paralelo-com-mapv
  "simulação utilizando mapv para forçar quase que imperativamente a execução do que era lazy"
    []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]]
    (mapv #(.start (Thread. (fn [] (chega-sem-malvado! hospital %))))pessoas)

                       (pprint hospital)))


;_________________________________________________________________________________________________
(defn simula-um-dia-em-paralelo-com-mapv-refatorada
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]
        starta-thread-de-chegada #(.start (Thread. (fn [] (chega-sem-malvado! hospital %))))]

    (mapv starta-thread-de-chegada pessoas)

          (.start (Thread. (fn [] (Thread/sleep 8000)
                             (pprint hospital))))))

(simula-um-dia-em-paralelo-com-mapv-refatorada)

;___________________________________________________________________________________
(defn starta-thread-de-chegada
  [hospital pessoa]
  (.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa)))))


(defn simula-um-dia-em-paralelo-com-mapv-com-partial
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]
        starta (partial starta-thread-de-chegada pessoas)]

    (mapv starta pessoas)

    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

(simula-um-dia-em-paralelo-com-mapv-com-partial)
;______________________________________________________________________________________________________
(defn simula-um-dia-em-paralelo-com-doseq
  "executa a sequencia"
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas (range 6)]
    (doseq [pessoas pessoas]
      (starta-thread-de-chegada hospital pessoas))

    (.start(Thread.(fn [] (Thread/sleep 8000)
                     (pprint hospital))))))

(simula-um-dia-em-paralelo-com-doseq)
;________________________________________________________________________________________________________
(defn simula-um-dia-em-paralelo-com-dotimes
  "Realmente estou preocupado em executar N vezes"
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (
      dotimes [pessoas 6]
      (starta-thread-de-chegada hospital pessoas))

    (.start(Thread.(fn [] (Thread/sleep 8000)
                     (pprint hospital))))))

(simula-um-dia-em-paralelo-com-dotimes)