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
    private void restartGame() {
        gameLogic = null;
        gameStarted = false;
        xmlEntered = false;
        gameEnded = false;
        start();
    }
    private void loopThroughGame() {
        int playerTurn = -1, choice = 0, min = 1, max = 1;
        Boolean validChoice = false;
        while (!gameEnded) {
            if (gameStarted) {
                min = 3;
                max = 7;
                playerTurn = gameLogic.getNumOfPlayer();
            }
            displayOptions(playerTurn);
            while (!validChoice) {
                try {
                    choice = getValidNumber(min, max);
                    validChoice = true;
                } catch (GameException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (gameStarted) {
                gameEnded = processChoice(choice);
                if(choice==4||choice==7)
                    gameLogic.endTurnClock(playerTurn);
            } else {
                gameEnded = processChoice(choice);
                if (xmlEntered)
                    max = 2;
            }
            validChoice = false;
        }
        System.out.println("player # "+ (1 + gameLogic.getNumOfPlayer()) +" won the game!!");
        exitGame();
    }


    private void startGame() {
        gameStarted = gameLogic != null;
        this.boardSize = gameLogic.getBoardSize();
    }

    private String[] createStringToPrint(String[] stringArr, Board[] boards) {
        int size = 2 * boardSize + stringArr.length + 6;
        String[] res = new String[size];
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
        res[stringArr.length + 1] = "Your board:";
        res[stringArr.length + boardSize + 3] = "\nAttempts board:";
        for (int i = 0; i < boards.length; i++) {
            int j = 0;
            for (String str:intBoardToString(boards[i])) {
                res[stringArr.length + i+1 + i*(boardSize+1) + ++j] = str;
            }
        }
        res[size - 1] = "v: Hit (If it's your board than your ship's been hit)\nx: Miss (If it's your board than your mine " +
                "been hit)\n!: Mine\n#: Ship";
        return res;
    }

    private String[] intBoardToString(Board board) {
        String []res = new String[boardSize+1];
        res[0] = boardSize>9?"  |":" |";
        for (int i = 1; i < boardSize + 1; i++)
            res[0] += ((boardSize >9 && i<10)? " ": "") +i +"|" ;
        for (int i = 1; i < boardSize + 1; i++) {
            res[ i ] = ((boardSize >9 && i<10) ? " " : "") + i + "|";
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
                if(boardSize>=10)
                    res[i]+= " ";
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
        menuStatus[ 1 ] = "Your Score is: " + gameLogic.getNumberOfHits(playerNum);
        printArray(createStringToPrint(menuStatus, myBoards));

    }

    private static void printArray(String[] array) {
        for (String str : array) {
            if(str != null)
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
                System.out.println("Hit!");
                break;
            case 0:
                System.out.println("Missed, you'll get them next time");
                break;
            case -1:
                System.out.println("Checked already");
                makeAMove();
                break;
            case -2:
                System.out.println("Mine hit!"); // need to do something
                gameLogic.mineHit(coord[ 0 ] - 1, coord[ 1 ] - 1);
                break;
            default:
                System.out.println("Great hit! ship is down, its score is " + (result - 2));
                if(gameLogic.isGameFinished())
                    gameEnded = true;
        }
    }

    private int[] getCoordination() throws GameException {
        int line,colum;
        System.out.println("Please enter two numbers(row, column) between 1 and " + boardSize);
        try {
            line = getValidNumber(1,boardSize);
        }
        catch (GameException ex){
            ex.setMsg("Your line choice is wrong, " + ex.getMessage());
            throw ex;
        }
        try {
            colum = getValidNumber(1,boardSize);
        }
        catch (GameException ex){
            ex.setMsg("Your column choice is wrong, " + ex.getMessage());
            throw ex;
        }
        return new int[]{line,colum};
    }

    private int getValidNumber(int minval, int maxval) throws GameException {
        int choice;
        try {
            choice = reader.nextInt();
        }
        catch (Exception ex){
            GameException exc = new GameException();
            exc.setMsg(String.format("Not a number ,please enter a number between %d and %d",minval,maxval));
            reader.nextLine();
            throw exc;
        }
        if (choice < minval || choice > maxval) {
            GameException ex = new GameException();
            ex.setMsg(String.format("Invalid choice! at this point you may only chose %d-%d",minval,maxval));
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
        menuStats[ 0 ] = "Number of turns: " + gameLogic.getNumOfTurns();
        menuStats[ 1 ] = "Time passed since the game started: (mm:ss)" + time[0] + ":" + time[1];
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
                xmlEntered = false;
                getXML();
                break;
            case 2:
                System.out.println("starting game! good luck :)");
                startGame();
                break;
            case 3:
                showGameStatus();
                break;
            case 4:
                gameLogic.startTurnClock();
                makeAMove();
                return gameEnded;
            case 5:
                showGameStatistics();
                break;
            case 6:
                System.out.println("better luck next time:)\nplayer # "+ ((1 - gameLogic.getNumOfPlayer())+1) +" won the game!!");
                exitGame();
            case 7:
                gameLogic.startTurnClock();
                putMine();
                break;
            default:
                break;
        }
        return false;
    }

    private void exitGame() {
        showGameStatistics();
        String playerOneBoards[][] = new String[2][];
        String playerTwoBoards[][] = new String[2][];
        playerOneBoards[0] = intBoardToString(gameLogic.getMyBoards(0)[0]);
        playerOneBoards[1] = intBoardToString(gameLogic.getMyBoards(0)[1]);
        playerTwoBoards[0] = intBoardToString(gameLogic.getMyBoards(1)[0]);
        playerTwoBoards[1] = intBoardToString(gameLogic.getMyBoards(1)[1]);
        String toPrint[] = new String[playerOneBoards[0].length + 1];
        StringBuilder spacing = new StringBuilder();
        int spacingLength = (playerOneBoards[0][0] + "  ").length() - ("Player #1").length();
        for(int i = 0; i < spacingLength; ++i) {
            spacing.append(" ");
        }
        toPrint[0] = "\nPlayer #1" + spacing.toString() + "Player #2";
        int i = 1;
        for(; i <= playerOneBoards[0].length; ++i) {
            toPrint[i] = playerOneBoards[0][i - 1] + "  " + playerTwoBoards[0][i - 1];
        }
        printArray(toPrint);
        System.out.println("Would you like to play again? y/n");
        char playAgain = 'n';
        boolean validChoice = false;
        while(!validChoice) {
            String choice = reader.next();
            playAgain = choice.charAt(0);
            if ((playAgain == 'Y' || playAgain == 'y' || playAgain == 'n' || playAgain == 'N') && choice.length() == 1 ) {
                validChoice = true;
            } else {
                System.out.println("Invalid choice! please enter y (yes) or n (no)");
            }
        }
        if (playAgain == 'Y' || playAgain== 'y') {
            restartGame();
        } else {
            System.exit(0);
        }
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
                System.out.println(String.format("Mine placed in (%d,%d) successfully",coord[0],coord[1])) ;
                break;
            case 0:
                System.out.println("Sorry, there are no mines left! make another move.");
                gameLogic.deleteTurn();
                loopThroughGame();
                break;
            case 2:
                System.out.println(String.format("You tried to put your mine in (%d,%d),which is not available! " +
                        "try another coordination:)",coord[0],coord[1]));
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
            String[][] playerBoards = new String[2][];
            playerBoards[0] = intBoardToString(gameLogic.getMyBoards(playerNum)[0]);
            playerBoards[1] = intBoardToString(gameLogic.getMyBoards(playerNum)[1]);
            printBoards(playerBoards);
            System.out.println(playerTemplate);
        } else {
            System.out.println(menuTemplate);
        }
    }
    private void printBoards(String[][] boards) {
        String toPrint[] = new String[boards[0].length + 1];
        StringBuilder spacing = new StringBuilder();
        int spacingLength = (boards[0][0]).length() - "Your board".length();
        for(int i = 0; i <= spacingLength; ++i) {
            spacing.append(" ");
        }
        toPrint[0] = "Your board" + spacing + "Attempts board";
        for(int i=1; i <= boards[0].length; ++i) {
            toPrint[i] = boards[0][i - 1] + "  " + boards[1][i - 1];
        }
        printArray(toPrint);
    }
}
