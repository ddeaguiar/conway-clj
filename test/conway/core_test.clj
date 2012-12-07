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

(fact "A 4 column row."
  (create-row 4) => [0 0 0 0])

(fact "A 4x4 board."
  (create-board 4 4) => [[0 0 0 0]
                         [0 0 0 0]
                         [0 0 0 0]
                         [0 0 0 0]])

(fact "An empty board populated with no living cells is empty."
  (populate-board (create-board 4 4)) => [[0 0 0 0]
                                          [0 0 0 0]
                                          [0 0 0 0]
                                          [0 0 0 0]])

(fact "An empty board should be populated with living cells."
  (populate-board (create-board 4 4) [0 1] [1 2] [2 3] [3 3]) => [[0 1 0 0]
                                                                  [0 0 1 0]
                                                                  [0 0 0 1]
                                                                  [0 0 0 1]])
