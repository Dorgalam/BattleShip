# BattleShip 
## By Lior Rokach & Dor Galam

>Based on the design of <https://battleship-game.org/en/> going for simplicity and good user experience.

Class diagram:
![diagram](https://i.imgur.com/SfpTM9z.png)
Logic:
* Main - creates an instance of ConsoleUI and lets it run
* ConsoleUI - handles receiving input from user etc
* Game - handles all game functions
* PlayerHistory - holds player objects for each turn to retrieve later
* Player - holds player details and its boards
* Board - holds two types of boards, the attempts board and the ship position board
* Ship - holds its score, location & more
* BattleShipParser - handles parsing XML & validating everything inside
* GameException - custom exception thrown to provide meaningful error messages
* Utils - holds static data & functions for Parser to use
* ShipType - basic information about a ship, length, name, amount etc.

FXUI:
* Context - singleton that holds all necessary data for the UI controllers
* Controllers - each of them handles the relevant FXML file behavior
* GridBase - base class for OpponentGridController & YourGridController, generates the board
* Transition effects - simple functions to add fade transition

Implemented bonuses:
1. Style themes

Programmers: 
* **Dor Galam**- 311232268 - dorgalam@gmail.com
* **Lior Rokach**- 312484900 - liorokach@gmail.com

