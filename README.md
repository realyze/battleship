# battleship

Simple implementation of Battleship game API implemented in Clojure.

### Notes
The API provides basic functionality for building a Battleship game. See
https://github.com/ambiata/interview/blob/master/battleship.md for details.


## Implementation Differences
We keep one board per player. A board holds information about player's ships
and enemy's attacks, i.e., the history of Player A's attacks is kept in Player
B's board (`:state` field of the `Cell` record). Displaying two boards for user
can be done in a straightforward manner by the application using the API.

We're using numeric indexes for the boards. It's easier and the code it
cleaner. The letter-to-number mapping should be implemented by the Battleship
game application using the API.

### Modules

You can require `battleship.core` and `battleship.game` in your app and use the
following public methods.

#### board module
 * `Cell`: Record with two fields: `state` (`:clear`, `:hit` or `:missed`)
   and `content` (`:ship` or `:empty`)
 * `place-ship board x y length horizontal?`: Places a ship with given length and
   orientation at coordinates x y. Returns updated board.
 * `create-board`: Returns an empty board.
 * `create-random-board`: Returns a board with randomly placed ships.
 * `get-cell board x y`: Returns cell at coordinates x y.
 * `set-cell board x y`: Sets cell at coordinates x y. Returns updated board.
 * (helper function) `display-board-content board`: Prints content of the given board.
 * (helper function) `display-board-state board`: Prints the state of the given board.

#### game module
 * `fire enemy-board x y`: Fires a missile at enemy board. Returns updated board and
   outcome (`:hit` or `:missed`).
 * `get-state-for-board board`: Returns either `:lost` or `:playing` depending
   on whether there are any unsunk ships left on the board.

### Running tests
Run 
```shell
lein midje
```
in console.


# Copyright
Copyright © 2015 Tomas Brambora

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
