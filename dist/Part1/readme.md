##BattleShip by Lior Rokach & Dor Galam

>Since this course's projects heavily rely on each other, we've decided to have the UI class as naive is possible and let the logic do all of the heavy lifting.
This is why our Logic project has 9 classes while the UI is one simple class.

Class diagram:
![diagram](http://i.imgur.com/I4W7oPh.png)
* Main - creates an instance of ConsoleUI and lets it run
* ConsoleUI - handles receiving input from user etc
* Game - handles all game functions
* Player - holds player details and its boards
* Board - holds two types of boards, the attempts board and the ship position board
* Ship - holds its score, location & more
* BattleShipParser - handles parsing XML & validating everything inside
* GameException - custom exception thrown to provide meaningful error messages
* Utils - holds static data & functions for Parser to use
* ShipType - basic information about a ship, length, name, amount etc.

Implemented bonuses:
1. Mines
2. Start new game after game ended

Programmers: 
**Dor Galam**- 311232268 - dorgalam@gmail.com
**Lior Rokach**- 312484900 - liorokach@gmail.com

