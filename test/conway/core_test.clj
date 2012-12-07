(ns conway.core-test
  (:use midje.sweet
        conway.core))

(unfinished )

(defn populate-board
  "Populates the board with the specified cells."
  [board & living]
  (if (empty? living) board
                      (apply populate-board (assoc-in board
                                                      (first living)
                                                      1)
                             (rest living))))

(defn create-row
  "Creates an empty row of col columns."
  [col]
  (vec (repeat col 0)))

(defn create-board
  "Creates an empty rows x col board."
  [rows col]
  (vec (repeat rows (create-row col))))

(defn neighbors
  "Returns the state of the given cell's neighbors."
  [board cell]
  (let [l (vector (first cell) (- (last cell) 1))
        t (vector (- (first cell) 1) (last cell))
        r (vector (first cell) (+ (last cell) 1))
        b (vector (+ (first cell) 1) (last cell))
        gets #(get-in board % 0)]
    [(gets l) (gets t) (gets r) (gets b)]))

(defn cell-state
  "Returns cell state based on state of neighbors."
  [n]
  (let [live (count (filter #(= 1 %) n))]
    (cond (> 2 live) 0
          (>= 3 live) 1
          :else 0)))


(def four-x-four (partial populate-board (create-board 4 4)))

(fact "A 4 column row."
  (create-row 4) => [0 0 0 0])

(fact "A 4x4 board."
  (create-board 4 4) => (four-x-four))

(fact "An empty board populated with no living cells is empty."
  (populate-board (create-board 4 4)) => (four-x-four))

(fact "An empty board should be populated with living cells."
  (four-x-four [0 1] [1 2] [2 3] [3 3]) => [[0 1 0 0]
                                            [0 0 1 0]
                                            [0 0 0 1]
                                            [0 0 0 1]])

(fact "A cell has four neighbors."
  (count (neighbors four-x-four [0 0])) => 4)

(fact "Two neighbors are alive."
  (neighbors [[0 1 0 0]
              [0 0 1 0]
              [0 0 0 0]
              [0 0 0 0]]
             [1 1]) => [0 1 1 0])

(fact "All neighbors are dead."
  (neighbors [[0 1 0 0]
              [0 0 1 0]
              [0 0 0 0]
              [0 0 0 0]]
             [1 0]) => [0 0 0 0])

(fact "All neighbors are alive."
  (neighbors [[0 1 0 0]
              [1 0 1 0]
              [0 1 0 0]
              [0 0 0 0]]
             [1 1]) => [1 1 1 1])

(fact "Any cell with fewer than two live neighbors dies due to under population."
  (cell-state [0 0 0 0]) => 0
  (cell-state [1 0 0 0]) => 0)

(fact "Any cell with two to three live neighbors lives on to the next generation."
  (cell-state [1 1 0 0]) => 1
  (cell-state [1 1 1 0]) => 1)

(fact "Any cell with more than three live neighbors dies due to overcrowding"
  (cell-state [1 1 1 1]) => 0)
