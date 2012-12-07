(ns conway.core-test
  (:use midje.sweet
        conway.core))

(unfinished )

(def four-x-four (partial populate-board (create-board 4 4)))
(def five-x-five (partial populate-board (create-board 5 5)))

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

(fact "A cell has eight neighbors."
  (count (neighbors four-x-four [0 0])) => 8)

(fact "Two neighbors are alive."
  (neighbors [[1 0 0 0]
              [0 0 0 0]
              [0 0 1 0]
              [0 0 0 0]]
             [1 1]) => [1 0 0 0 1 0 0 0])

(fact "All neighbors are dead."
  (neighbors [[0 0 0 0]
              [0 0 0 0]
              [0 0 0 0]
              [0 0 0 0]]
             [1 0]) => [0 0 0 0 0 0 0 0])

(fact "All neighbors are alive."
  (neighbors [[1 1 1 0]
              [1 0 1 0]
              [1 1 1 0]
              [0 0 0 0]]
             [1 1]) => [1 1 1 1 1 1 1 1])

(fact "Any live cell with fewer than two live neighbors dies due to under population."
  (cell-state 1 [0 0 0 0 0 0 0 0]) => 0
  (cell-state 1 [1 0 0 0 0 0 0 0]) => 0)

(fact "Any live cell with two to three live neighbors lives on to the next generation."
  (cell-state 1 [1 1 0 0 0 0 0 0]) => 1
  (cell-state 1 [1 1 1 0 0 0 0 0]) => 1)

(fact "Any live cell with more than three live neighbors dies due to overcrowding"
  (cell-state 1 [1 1 1 1 1 1 1 1]) => 0)

(fact "A dead cell with exactly three live neighbors lives as if reproduction occurred."
  (cell-state 0 [1 1 1 0 0 0 0 0]) => 1
  (cell-state 0 [1 1 0 0 0 0 0 0]) => 0
  (cell-state 0 [1 1 1 1 0 0 0 0]) => 0
  (cell-state 0 [1 1 1 1 1 1 1 1]) => 0
  (cell-state 0 [0 0 0 0 0 0 0 0]) => 0
  )

;(def box fourxfour [1 1] [1 2] [2 1] [2 2])
(def blinker [[0 0 0 0 0]
              [0 0 1 0 0]
              [0 0 1 0 0]
              [0 0 1 0 0]
              [0 0 0 0 0]])

(def blinker-blinked [[0 0 0 0 0]
                      [0 0 0 0 0]
                      [0 1 1 1 0]
                      [0 0 0 0 0]
                      [0 0 0 0 0]])

(fact "Blinker should 'blink'."
  (tick blinker) => blinker-blinked
  (take 1 (ticks blinker)) => [blinker]
  (take 2 (ticks blinker)) => [blinker blinker-blinked]
  (take 3 (ticks blinker)) => [blinker blinker-blinked blinker])
