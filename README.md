# PokemonProject
Basic pokemon game building kit that will allow for custom battles, pokemon, etc.  I'm building on a specific scenario and files will be related to that, but could easily be adapted for individual projects

Work done to date:
Created "Move" and "Pokemon" class, each with the necessary framework to construct moves and creatures to use those moves.
Created the main class, began constructing the battle logic.  

Battle framework as of 4/17/2015: (within work desktop today file)
Battle is passed a File and a Player who is the controllable character in the battle
Using the given format, reads data from the given file to create an enemy player object
Use display function to fade into the battle screen, changes display nature, intialize 
challenge message, and show player icon movement.
Enters the while loop, based on a win or loss condition that **will be the result of a complete faint of either team,
** or possibly a capture condition
Basic menu navigation ability is beginning, the menu logic will take some time to work out.  I'll probably
make the initial very specific, but I think i'll build a menubuilder class to make things more modular in
the future.
