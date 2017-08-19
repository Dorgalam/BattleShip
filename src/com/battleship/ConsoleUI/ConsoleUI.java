package com.battleship.ConsoleUI;

import com.battleship.Logic.Board;
import com.battleship.Logic.Game;
import com.battleship.Logic.GameException;

import java.lang.ref.Reference;
import java.sql.Ref;
import java.util.Arrays;
import java.util.Scanner;

public class ConsoleUI {
    private Game gameLogic = null;
    private boolean gameStarted = false;
    //private int playerNum = -1;
    private int boardSize;
    private Scanner reader = new Scanner(System.in);
    private boolean xmlEntered = false;
    private boolean gameEnded = false;

    public void start() {
        System.out.println("Welcome to Lior and Dor's awesome BattleShip game!!");
        loopThroughGame();

    }

    private void loopThroughGame() {
        int playerTurn = -1;
        int choice;
            while (!gameEnded) {
                if(gameStarted) {
                    playerTurn = gameLogic.getNumOfPlayer();
                    displayOptions(gameLogic.getNumOfPlayer());
                }
                else
                    displayOptions(-1);
                choice = reader.nextInt();
                if (choice < 0 || choice > 7)
                    System.out.println("Invalid input, please enter a number between 1 and 7");
                else {
                    gameEnded = processChoice(choice);
                    if(playerTurn!=-1)
                        gameLogic.endTurnClock(playerTurn);
                }
        }
        System.exit(0);

    }

    private void startGame() {
        gameStarted = gameLogic != null;
        this.boardSize = gameLogic.getBoardSize();
    }

    private String[] createStringToPrint(String[] stringArr, Board[] boards) {
        String[] res = new String[ 2 * boardSize + stringArr.length + 5 ];
        int maxLen = (boardSize+1)*2;
        for (String str : stringArr) {
            if (maxLen < str.length())
                maxLen = str.length();
        }
        maxLen += 2;
        for (int i = 0; i < stringArr.length; i++) {
            res[i+1] = "|" + stringArr[i];
            for (int j = stringArr[i].length(); j < maxLen; j++) {
                res[ i + 1 ] += " ";
            }
            res[ i + 1 ] += "|";
        }
        res[stringArr.length + 1] = "your board:";
        res[stringArr.length + boardSize + 3] = "trying board:";
        for (int i = 0; i < boards.length; i++) {
            int j = 0;
            for (String str:intBoardToString(boards[i])) {
                res[stringArr.length + i+1 + i*(boardSize+1) + ++j] = str;
            }
        }
        return res;
    }

    private String[] intBoardToString(Board board) {
        String []res = new String[boardSize+1];
        res[0] = " |";
        for (int i = 1; i < boardSize + 1; i++)
            res[0] += i+"|";
        for (int i = 1; i < boardSize + 1; i++) {
            res[ i ] = i + "|";
            int[] line = board.getLine(i-1);
            for (int var : line) {
                switch (var) {
                    case Board.EMPTY_CELL:
                        res[ i ] += " ";
                        break;
                    case Board.HIT:
                        res[ i ] += "v";
                        break;
                    case Board.MISS:
                        res[ i ] += "x";
                        break;
                    case Board.MINE:
                        res[ i ] += "!";
                        break;
                    default:
                        res[ i ] += "#";
                        break;
                }
                res[i]+="|";
            }
        }
        return res;
    }

    private void showGameStatus(){
        int playerNum = gameLogic.getNumOfPlayer();
        Board[] myBoards = gameLogic.getMyBoards(playerNum);
        String[] menuStatus = new String[ 2 ];
        menuStatus[ 0 ] = "Player #" + (playerNum + 1) + "'s turn";
        menuStatus[ 1 ] = "Your Score is: " + gameLogic.getScore(playerNum);
        printArray(createStringToPrint(menuStatus, myBoards));

    }

    private static void printArray(String[] array) {
        for (String str : array) {
            System.out.println(str);
        }
    }

    private void makeAMove() {
        displayQuestion();
        int[] coord;
        try {
            coord = getCoordination();
        } catch (GameException ex) {
            System.out.println(ex.getMessage());
            makeAMove();
            return;
        }
        int result = gameLogic.makeTurn(coord[ 0 ] - 1, coord[ 1 ] - 1);
        switch (result) {
            case 1:
                System.out.println("hit!");
                if(gameLogic.isGameFinished()){
                    System.out.println("player #"+gameLogic.getNumOfPlayer()+" is won the game");
                    showGameStatistics();
                    gameEnded = true;
                }
                break;
            case 0:
                System.out.println("missed,try better next time:)");
                break;
            case -1:
                System.out.println("checked already");
                break;
            case -2:
                System.out.println("mine hit!"); // need to do something
                gameLogic.mineHit(coord[ 0 ] - 1, coord[ 1 ] - 1);
                break;
            default:
                System.out.println("great hit you took one ship down: ship points = " + (result - 2));
        }
    }

    private int[] getCoordination() throws GameException {
        int line,colum;
        System.out.println("please enter two numbers(first -> line,second -> column) between 1 to " + boardSize);
        try {
            line = getValidNumber();
        }
        catch (GameException ex){
            ex.setMsg("your line choice is wrong, " + ex.getMessage());
            throw ex;
        }
        try {
            colum = getValidNumber();
        }
        catch (GameException ex){
            ex.setMsg("your column choice is wrong, " + ex.getMessage());
            throw ex;
        }
        return new int[]{line,colum};
    }

    private int getValidNumber() throws GameException {
        int choice;
        try {
            choice = reader.nextInt();
        }
        catch (Exception ex){
            GameException exc = new GameException();
            exc.setMsg(String.format("Not a number ,please enter a number between 1 and %d",boardSize));
            reader.nextLine();
            throw exc;
        }
        if (choice < 1 || choice > boardSize) {
            GameException ex = new GameException();
            ex.setMsg(String.format("you chose number: %d,out of range - please enter a number between 1 and %d",choice,boardSize));
            throw ex;
        }
        return choice;
    }
    private void displayQuestion() {
        System.out.println("Player #" + (gameLogic.getNumOfPlayer() + 1));
    }

    private void showGameStatistics() {
        String[] menuStats = new String[ 8 ];
        long [] time =  gameLogic.GetTimePass();
        menuStats[ 0 ] = "Number of Turns: " + gameLogic.getNumOfTurns();
        menuStats[ 1 ] = "Time Passed from the beginning of the game: " + time[0] + ":" + time[1];
        menuStats[2] = "player #1 score is :" + gameLogic.getNumberOfHits(0);
        menuStats[3] = "player #2 score is :" + gameLogic.getNumberOfHits(1);
        menuStats[4] = "player #1 missed #:" + gameLogic.getNumberOfMisses(0);
        menuStats[5] = "player #2 missed #:" + gameLogic.getNumberOfMisses(1);
        menuStats[6] = "player #1 average turn time is: " + gameLogic.getAvgTime(0);
        menuStats[7] = "player #2 average turn time is: " + gameLogic.getAvgTime(1);
        printArray(menuStats);
    }


    private boolean processChoice(int choice) {
        switch (choice) {
            case 1:
                getXML();
                break;
            case 2:
                if (xmlEntered) {
                    startGame();
                } else {
                    System.out.println("Error: a valid XML must be entered first");
                }
                break;
            case 3:
                if (gameStarted) {
                    showGameStatus();
                } else {
                    System.out.println("Error: game hasn't started yet. cannot show status");
                }
                break;
            case 4:
                if (gameStarted) {
                    makeAMove();
                } else {
                    System.out.println("Error: game hasn't started yet. cannot make a move");
                }
                break;
            case 5:
                if (gameStarted) {
                    showGameStatistics();
                } else {
                    System.out.println("Error: game hasn't started yet. cannot show statistics");
                }
                break;
            case 6:
                System.out.println("Better luck next time!");
                return true;
            case 7:
                if (gameStarted) {
                    putMine();
                } else {
                    System.out.println("Error: game hasn't started yet. cannot add a mine");
                }
                break;
            default:
                break;
        }

        return false;
    }

    private void putMine() {
        displayQuestion();
        int[] coord;
        try {
            coord = getCoordination();
        } catch (GameException ex) {
            System.out.println(ex.getMessage());
            putMine();
            return;
        }
        int result = gameLogic.putMine(coord[ 0 ] - 1, coord[ 1 ] - 1);
        switch (result) {
            case 1:
                System.out.println(String.format("mine located now in (%d,%d) and you have mine left",coord[0],coord[1])) ;
                break;
            case 0:
                System.out.println("sorry, there are no mines left!,make another move.");
                loopThroughGame();
                break;
            case 2:
                System.out.println(String.format("you tried to put your mine in (%d,%d),which is not available +" +
                        "try another coordination",coord[0],coord[1]));
                putMine();
                break;
            default:
                System.out.println("Wrong");
                break;
        }

    }

    private void getXML() {
        while (!xmlEntered) {
            try {
                System.out.println("Please write down your XML's location so we can begin (c:\\DIR\\DIR\\data.xml)");
                // src/resources/battleShip_5_basic.xml
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
                        "|   1. Enter an XML file        4. Make a move              7.Put mine     |\n" +
                        "|   2. Start game               5. Show game statistics                    |\n" +
                        "|   3. Show game status         6. End game                                |\n" +
                        "****************************************************************************\n";
        final String playerTemplate =
                "*****************************************************************************************\n" +
                        "|                    |    1. Enter an XML file     4. Make a move           7.Put mine  |\n" +
                        "|  Player #" + (playerNum + 1) + "'s turn  |    2. Start game            5. Show game statistics              |\n" +
                        "|                    |    3. Show game status      6. End game                          |\n" +
                        "*****************************************************************************************\n";
        if (playerNum != -1) {
            System.out.println(playerTemplate);
            gameLogic.startTurnClock();
        } else {
            System.out.println(menuTemplate);
        }
    }
}
