# conway

My implementation of Conway's game of life in clojure.

## Installation

lein deps

It's assumed that midje is referenced in your .lein/profiles.clj

## Usage

For now just running tests is supported.

   $ lein midge --lazytest

## Examples

    (def beehive [[0 0 0 0 0 0]
                  [0 0 1 1 0 0]
                  [0 1 0 0 1 0]
                  [0 0 1 1 0 0]
                  [0 0 0 0 0 0]])

    (take 3 (ticks beehive)) ;; => returns 3 generations of the boardstate seeded with the 'beehive' configuration.
   
### TODO

  Add a graphical component!

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2012 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
