;;
;; Functions
;;
(defn constant [x] (fn [args] x))
(defn variable [name] (fn [args] (args name)))

(defn op [f]
  (fn [& e]
    (fn [args]
      (apply f (mapv
                 (fn [x] (x args)) e)))))

(defn unary [f]
  (fn [a]
    (fn [args]
      (f (a args)))))

(def add      (op +))
(def subtract (op -))
(def multiply (op *))
;(def divide   (op /))

(defn divide [a b]
  (fn [args] (/ (double (a args)) (double (b args)))))

(defn cos' [x] (Math/cos x))
(defn sin' [x] (Math/sin x))

(def negate (unary -))
(def cos    (unary cos'))
(def sin    (unary sin'))

;;
;;Objects
;;
(defn constructor [ctor methods]
  (fn [& args] (apply (partial ctor {:methods methods}) args)))

(defn diff     [this name] (((this :methods) :diff) this name))
(defn evaluate [this args] (((this :methods) :evaluate) this args))
(defn toString [this]      (((this :methods) :toString) this))


(defn Const [this value]
  (assoc this
    :value value))

(def Constant (constructor Const
                {:evaluate (fn [this args] (this :value))
                 :diff     (fn [this name] (Constant 0))
                 :toString (fn [this]      (str (this :value)))}))

(defn Var [this name]
  (assoc this
    :name name))

(def Variable (constructor Var
                {:evaluate (fn [this args] (args (this :name)))
                 :diff     (fn [this name] (if (= (this :name) name) (Constant 1) (Constant 0)))
                 :toString (fn [this]      (this :name))}))

(defn getOperator [f name diff]
  {:evaluate (fn [this args] (apply f (mapv
                                        (fn [x] (evaluate x args))
                                        (this :operands))))
   :toString (fn [this]      (cons name (mapv
                                          (fn [x] (toString x)) (this :operands))))
   :diff     (fn [this name] (diff (this :operands) name))
   })

(defn Operation [this & operands]
  (assoc this
    :operands operands))

(def Add      (constructor Operation (getOperator + "+"
                                       (fn [f s] (apply Add (mapv (fn [x] (diff x s)) f))))))
(def Subtract (constructor Operation (getOperator - "-"
                                       (fn [f s] (apply Subtract (mapv (fn [x] (diff x s)) f))))))
(def Multiply (constructor Operation (getOperator * "*"
                                       (defn mult-diff [f s] (cond (empty? (rest f)) (diff (first f) s) :else (Add (apply Multiply (cons (diff (first f) s) (rest f))) (Multiply (first f) (mult-diff (rest f) s))))))))
(def Divide   (constructor Operation (getOperator / "/"
                                       (fn [f s] (Divide (Subtract (Multiply (diff (first f) s) (second f)) (Multiply (diff (second f) s) (first f))) (Multiply (second f) (second f)))))))
(def Negate   (constructor Operation (getOperator - "negate"
                                       (fn [f s] (Negate (diff (first f) s))))))
(def Cos      (constructor Operation (getOperator cos' "cos"
                                       (fn [f s] (Multiply (Cos (Add (first f) (Constant 1.5707963))) (diff (first f) s))))))
(def Sin      (constructor Operation (getOperator sin' "sin"
                                       (fn [f s] (Multiply (Sin (Add (first f) (Constant 1.5707963))) (diff (first f) s))))))

;;
;; Parsers
;;
(defn parse [operators c v] (defn parseImpl [item] (cond
                                         (number? item) (c item)
                                         (symbol? item) (v (str item))
                                         (list?   item) (apply
                                                          (operators (first item))
                                                          (mapv parseImpl (rest item)))
                                                   )))

(def fOperators {'+ add '- subtract '* multiply '/ divide 'negate negate 'cos cos 'sin sin})
(def oOperators {'+ Add '- Subtract '* Multiply '/ Divide 'negate Negate 'cos Cos 'sin Sin})

(defn parseFunction [s] ((parse fOperators constant variable) (read-string s)))
(defn parseObject   [s] ((parse oOperators Constant Variable) (read-string s)))


