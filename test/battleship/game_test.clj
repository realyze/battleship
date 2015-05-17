(ns battleship.game-test
    (:use midje.sweet) 
    (:require [battleship.game :as game]
              [battleship.board :as board])
    (:import [battleship.board Cell]))

(facts "about fire"

  (facts "if given cell contains a ship"
    (let [board (board/create-board)
          board-with-ship (board/place-ship board 1 1 5 true)
          [board-after-fire outcome] (game/fire board-with-ship 1 1)]

      (fact "it returns a new board with cell marked as :hit"
        (board/get-cell board-after-fire 1 1) => (board/Cell. :ship :hit)

      (fact "it returns `:hit` as the outcome"
        outcome => :hit))))

  (facts "if given cell is empty"
    (let [board (board/create-board)
          [board-after-fire outcome] (game/fire board 1 1)]

      (fact "it returns a new board with cell marked as :missed"
        (board/get-cell board-after-fire 1 1) => (board/Cell. :empty :missed)

      (fact "it returns `:missed` as the outcome"
        outcome => :missed)))))

(facts "about get-state-for-board"
  
  (facts "if board has unhit ships"
    (let [board (board/create-board)
          board-with-ship (board/place-ship board 1 1 5 true)]
      (fact "it returns `:playing`"
        (game/get-state-for-board board-with-ship) => :playing)))
  
  (facts "if all ships are sunk"
    (let [board (board/create-board)
          board-with-ship (board/place-ship board 1 1 2 true)
          board-with-sunk-ship (reduce #(board/set-cell %1 %2 1 (board/Cell. :ship :hit)) board-with-ship [1 2])]
      (fact "it returns `:lost`"
        (game/get-state-for-board board-with-sunk-ship) => :lost))))
