;; Game module
;; Exposes functions that deal with game state - firing at enemy & getting
;; player's state (lost or playing).

(ns battleship.game
    (:require [battleship.board :as board])
    (:gen-class))

(defn fire
  "Fires at enemy board. Returns a new board and the outcome (`:missed` or `:hit`)"
  [enemy-board x y]
  (let [cell (board/get-cell enemy-board x y)]
    (let [outcome (if (= (:content cell) :ship) :hit :missed)]
      [(board/set-cell enemy-board x y (assoc cell :state outcome)) outcome])))

(defn get-state-for-board
  "Returns the game state for `board`: either `:playing` or `:lost`."
  [board]
  (let [ship-cells (filter #(= (:content %) :ship) (flatten board))
        unsunk-ship-cells (remove #(= (:state %) :hit) ship-cells)]
    (if (> (count unsunk-ship-cells) 0) :playing :lost)))
