(ns hospital.logic)

(defn cabe-na-fila? [hospital departamento]
  (-> hospital
      (get  ,,, departamento)
      count,,,
      (<,,, 5)))

(defn chega-em
  [hospital departamento pessoa]
  (let [fila (get hospital departamento)
        tamanho-atual (count fila)
        cabe? (< tamanho-atual 5)]
    (if cabe?
      (update hospital departamento conj pessoa)
      (throw (ex-info "Fila já está cheia" { :tentando-adicionar pessoa})))))

(defn chega-em-pausado
  [hospital departamento pessoa]
  (Thread/sleep (*(rand)2000))
  (if (cabe-na-fila? hospital departamento)
    (do (Thread/sleep 1000))
      (update hospital departamento conj pessoa))
      (throw (ex-info "Fila já está cheia" { :tentando-adicionar pessoa})))


(defn chega-em-pausado-logando
  [hospital departamento pessoa]
  (println "tentando adcionar esta pessoa"pessoa)
  (Thread/sleep (* (rand)2000))
  (if (cabe-na-fila? hospital departamento)
    (do
      ;(Thread/sleep 1000))
    (update hospital departamento conj pessoa)
      (println "Dei update" pessoa))
  (throw (ex-info "Fila já está cheia" { :tentando-adicionar pessoa}))))


(defn atende
  [hospital departamento]
  (update hospital departamento pop))

(defn proxima
  [hospital departamento]
  (-> hospital
      departamento
      peek))

(defn transfere
  [hospital de para]
  (let [pessoa (peek (get hospital de))]
    (-> hospital
        (atende de)
        (chega-em para pessoa))))

(defn atende-completo
  "somente para demosntrar que é possivel retornar os dois (quem e a fila restante.)"
  [hospital departamento]
  {:paciente (update hospital departamento peek)
   :hospital (update hospital departamento pop)})

(defn atende-completo-que-chama-ambos
  [hospital departamento]
  (let [fila (get hospital departamento)
        peek-pop (juxt peek pop)
        [pessoa fila-atualizada] (peek-pop fila)
        hospital-atualizado (update assoc departamento fila-atualizada)]
    {:paciente pessoa
     :hospital hospital-atualizado}))