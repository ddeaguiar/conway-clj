(ns conway.core)

(def live 1)
(def dead 0)

(defn live? [cs] (= live cs))
(defn dead? [cs] (= dead cs))

(defn populate-board
  "Populates the board with the specified cells."
  [board & living]
  (if (empty? living) board
                      (apply populate-board (assoc-in board
                                                      (first living)
                                                      live)
                             (rest living))))

(defn create-row
  "Creates an empty row of col columns."
  [col]
  (vec (repeat col dead)))

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
        gets #(get-in board % dead)]
    [(gets lt) (gets t) (gets tr) (gets r) (gets br) (gets b) (gets bl) (gets l)]))

(defn cell-state
  "Returns cell state based on state of neighbors and cell state."
  [cell-state cell-neighbors]
  (let [count-live-neighbors (count (filter #(= live %) cell-neighbors))]
    (cond (> 2 count-live-neighbors) dead
          (and (live? cell-state) (>= 3 count-live-neighbors)) live
          (and (dead? cell-state) (== 3 count-live-neighbors)) live
          :else dead)))

(defn tick
  "Advances the board state by one tick (generation)."
  [board]
  (vec (map-indexed (fn [r-index row]
                       (vec (map-indexed #(cell-state %2
                                                      (neighbors board
                                                                 [r-index % %2]))
                                         row)))
                     board)))

(defn ticks
  "Returns a lazy sequence of board states."
  [board]
  (cons board (lazy-seq (ticks (tick board)))))
