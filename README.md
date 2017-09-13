[![Build Status](https://travis-ci.org/MartinLei/Arimaa.svg?branch=master)](https://travis-ci.org/MartinLei/Arimaa)
# Arimaa
This is a implementation of the Arimaa game.\
I used it as a playground to get in touch with scala.

# Infrastructure
- simultaneously run of tui and gui (in progress)
- mvc, tdd , bdd and solid featured on deployment

# License
This code project is under MIT License.\
For future licences of the game itself, look up at [Arimaa](http://arimaa.com/).


# TODO
## inf
- get list of moves
- start change tile set
## rules
- coordinate should only be one tile distance
- freeze unlock if surround by one, own tile
- 4 steps per round (player)
- detect third time same move
- victory

## bug
- cant pull a tile backwards "its not your tile"
- cant push a tile forward "its not your tile"
 