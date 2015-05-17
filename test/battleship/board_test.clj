(ns battleship.board-test
    (:use midje.sweet) 
    (:require [battleship.board :as board])
    (:import [battleship.board Cell]))

(facts "about get-cell"

  (fact "it returns the state the cell at given coordinates"
    (let [board board/board
          updated-board (assoc board 1 (assoc (nth board 1) 1 (Cell. :ship :missed)))]
      (board/get-cell board 0 0) => (Cell. :empty :clear)
      (board/get-cell updated-board 1 1 ) => (Cell. :ship :missed)))

  (fact "it throws an IndexOutOfBoundsException if coordinates are off the board"
    (board/get-cell board/board 1000 1000) => (throws IndexOutOfBoundsException)))

(facts "about set-cell"

  (fact "it sets the state of the cell at given coordinates"
    (let [board board/board
          cell (board/get-cell board 1 1)
          new-cell (assoc cell :content :ship)
          new-board (board/set-cell board 1 1 new-cell)]
      ;; Check original cell.
      cell => (Cell. :empty :clear)
      ;; Check it has been updated.
      (board/get-cell new-board 1 1) => (Cell. :ship :clear)))

  (fact "it throws an IndexOutOfBoundsException if coordinates are off the board"
    (board/set-cell board/board 1000 1000 (Cell. :ship :clear)) => (throws IndexOutOfBoundsException)))

(facts "about place-ship"

  (fact "horizontal placement"

    (fact "it places the ship with given length and orientation to coordinates"
      (let [board board/board
            board-with-ship (board/place-ship board 1 1 5 true)
            row (nth board-with-ship 1)
            cells (subvec row 1 6)]
        cells => (just (vec (take 5 (repeat (Cell. :ship :clear))))))))

    (fact "it throws an IndexOutOfBoundsException if ship does not fit on the board"
      (board/place-ship board/board 7 1 5 true) => (throws IndexOutOfBoundsException))

  (fact "vertical placement"

    (fact "it places the ship with given length and orientation to coordinates"
      (let [board board/board
            board-with-ship (board/place-ship board 1 1 5 false)
            col (vec (map #(nth % 1) board-with-ship))
            cells (subvec col 1 6)]
        cells => (just (vec (take 5 (repeat (Cell. :ship :clear)))))))

    (fact "it throws an IndexOutOfBoundsException if ship does not fit on the board"
      (board/place-ship board/board 1 7 5 false) => (throws IndexOutOfBoundsException))))

(facts "about create-random-board"

  (fact "it creates a board with all ships"
    (let [random-board (board/create-random-board)
          expected-ship-cells (reduce + (vals board/ships))]
      (count (filter #(= (:content %) :ship) (flatten random-board))) => expected-ship-cells))

  (fact "two subsequent calls create different boards"
    (board/create-random-board) =not=> (board/create-random-board)))
