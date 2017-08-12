package com.battleship.ConsoleUI;

import com.battleship.Logic.Game;
import com.battleship.Logic.GameException;

import java.util.Scanner;

public class ConsoleUI {
    private Game gameLogic = null;
    private boolean gameStarted = false;
    private int playerNum = -1;
    private Scanner reader = new Scanner(System.in);
    public void start() {
        System.out.println("Welcome to Lior and Dor's awesome BattleShip game!!");
        loopThroughGame();

    }
    private void loopThroughGame() {
        boolean gameEnded = false;
        int choice;
        while (!gameEnded) {
            displayOptions(playerNum);
            choice = reader.nextInt();
            if (choice < 0 || choice > 6) {
                System.out.println("Invalid input, please enter a number between 1 and 6");
            } else {
                gameEnded = processChoice(choice);
            }
        }
    }
    private void startGame() {
        gameStarted = gameLogic != null;
        playerNum = gameLogic.getNumOfPlayer();
    }
    private void showGameStatus() {

    }
    private void makeAMove() {
        System.out.println("Please write your next attack coordinates");
        System.out.println("Row: (A-Z)");
        reader.next();
        playerNum = gameLogic.getNumOfPlayer();

    }
    private void showGameStatistics() {

    }
    private void exitGame() {

    }


    private boolean processChoice(int choice) {
        switch(choice) {
            case 1:
                getXML();
                break;
            case 2:
                startGame();
                break;
            case 3:
                showGameStatus();
            case 4:
                makeAMove();
                break;
            case 5:
                showGameStatistics();
                break;
            case 6:
                exitGame();
                return true;
            default:
                break;
        }
        return false;
    }
    private void getXML() {
        boolean xmlEntered = false;
        while (!xmlEntered) {
            try {
                System.out.println("Please write down your XML's location so we can begin (c:/DIR/DIR/data.xml)");
                 //src/resources/battleShip_5_basic.xml
                gameLogic = new Game(reader.next());
                System.out.println("Success!");
                xmlEntered = true;
            } catch (Exception e) {
                System.out.println("\n" + e.getMessage());
                System.out.println("Please try again.\n");
            }
        }
    }
    private void displayOptions(int playerNum) {
        final String menuTemplate =
                "****************************************************************************\n" +
                "|   1. Enter an XML file        4. Make a move                             |\n" +
                "|   2. Start game               5. Show game statistics                    |\n" +
                "|   3. Show game status         6. End game                                |\n" +
                "****************************************************************************\n";
        final String playerTemplate =
                "****************************************************************************\n" +
                "|                    |    1. Enter an XML file     4. Make a move          |\n" +
 "|  Player #" + (playerNum + 1) + "'s turn  |    2. Start game            5. Show game statistics |\n" +
                "|                    |    3. Show game status      6. End game             |\n" +
                "****************************************************************************\n";
        if (playerNum != -1) {
            System.out.println(playerTemplate);
        } else {
            System.out.println(menuTemplate);
        }
    }
}
