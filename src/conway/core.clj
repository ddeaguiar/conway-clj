(ns conway.core
  (:gen-class))

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
  (let [left     #(vector (first %) (- (second %) 1))
        top      #(vector (- (first %) 1) (second %))
        right    #(vector (first %) (+ (second %) 1))
        bottom   #(vector (+ (first %) 1) (second %))
        lt       (left (top cell))
        t        (top cell)
        tr       (top (right cell))
        r        (right cell)
        br       (bottom (right cell))
        b        (bottom cell)
        bl       (bottom (left cell))
        l        (left cell)
        gets #(get-in board % 0)]
    [(gets lt) (gets t) (gets tr) (gets r) (gets br) (gets b) (gets bl) (gets l)]))

(defn cell-state
  "Returns cell state based on state of neighbors and cell state."
  [cell-state cell-neighbors]
  (let [count-live-neighbors (count (filter #(= 1 %) cell-neighbors))]
    (cond (> 2 count-live-neighbors) 0
          (and (= 1 cell-state) (>= 3 count-live-neighbors)) 1
          (and (= 0 cell-state) (== 3 count-live-neighbors)) 1
          :else 0)))

(defn tick
  [board]
  (vec (map-indexed (fn [r-index row]
                       (vec (map-indexed #(cell-state %2
                                                      (neighbors board
                                                                 [r-index % %2]))
                                         row)))
                     board)))

(defn ticks
  [board]
  (cons board (lazy-seq (ticks (tick board)))))
