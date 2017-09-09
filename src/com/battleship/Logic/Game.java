package com.battleship.Logic;

import java.util.ArrayList;

public class Game {
    private long startGameTime = 0;
    private Player[] players;
    private long startTurnTime = 0;
    private int boardSize;
    private int numOfPlayer = 0;
    private String playerNames[];
    private int gameMode;
    public static final int BASIC = 0;
    public static final int ADVANCED = 1;

    public Game(String xmlPath) throws Exception {
        try {
            this.startGameTime = java.time.Instant.now().getEpochSecond();
            this.players = new Player[ 2 ];
            BattleShipParser parser = new BattleShipParser(xmlPath);
            this.boardSize = parser.getBoardSize();
            this.gameMode = parser.getGameType().equals("BASIC") ? BASIC : ADVANCED;
            this.players[ 0 ] = new Player(boardSize, parser.getBoardAShips());
            this.players[ 1 ] = new Player(boardSize, parser.getBoardBShips());
        }
        catch (GameException ex){
            throw ex;
        }
    }

    public boolean isValidPlaceForMine(int x, int y) {
        return players[numOfPlayer].checkMineLoc(new Point(x,y));
    }

    public void setPlayerNames(String[] names) {
        playerNames = names;
    }

    public String getPlayerName() {
        return  playerNames[numOfPlayer];
    }

    public String getPlayerName(int num) {
        return  playerNames[num];
    }

    public Ship[] getPlayerShips() {
        return players[numOfPlayer].getMyShips();
    }

    public Game(Player player1, Player player2) {
        this.startGameTime = java.time.Instant.now().getEpochSecond();
        this.players = new Player[2];
        this.players[0] = player1;
        this.players[1] = player2;
    }

    public void startTurnClock() {
        startTurnTime = java.time.Instant.now().getEpochSecond();
        players[numOfPlayer].incTurn();
    }

    public ArrayList<Ship> getDestroyedShips (){
        return players[1 - numOfPlayer].getDestroyedShips();
    }

    public void deleteTurn(){
        players[numOfPlayer].decTurns();
    }

    public void endTurnClock(int num){
        long end = java.time.Instant.now().getEpochSecond();
        players[num].setAvgTimeOfTurn(end-startTurnTime);
    }

    public int makeTurn(int x, int y)
    {
        Point p = new Point(x,y);
        int res;
        if (players[numOfPlayer].isAlreadyChecked(p)) {
            if(players[1-numOfPlayer].getMyBoard().getSquare(p)!=Board.MINE)
                res = -1;
            else {
                res = -2;
            }
        } else {
            res = players[1 - numOfPlayer].checkHit(p);
        }
        if (res >= 1) {
            players[numOfPlayer].incHit(p);
        } else if (res == 0) {
            players[numOfPlayer].incMiss(p);
            numOfPlayer = 1 - numOfPlayer;
        }
        return res; // 2 = ship down ,  1 = HIT , 0 = MISS , -1 = already checked
    }


    public void mineHit(int x, int y) {
        Point point = new Point(x,y);
        players[numOfPlayer].incHit(point);
        numOfPlayer = 1- numOfPlayer;
        int tmp = numOfPlayer;
        makeTurn(point.getX(),point.getY());
        numOfPlayer = tmp;
    }

    public int putMine(int x, int y) {
        if (players[numOfPlayer].isMinesLeft()) {
            if (players[numOfPlayer].isValidPlaceForMine(new Point(x,y))) {
                numOfPlayer = 1 - numOfPlayer;
                return 1; // valid place + mine left
            }
            return 2; // mines left + is not valid place
        }
        return 0; // there are no mines left
    }

    public int getNumMines() {
        return players[numOfPlayer].getNumMines();
    }

    public boolean isGameFinished(){
        return players[1 - numOfPlayer].allShipsDown();
    }

    public int getNumOfTurns(){
        return players[0].getNumberOfTurns() + players[1].getNumberOfTurns();
    }

    public int getNumberOfHits(int numOfPlayer){
        return players[numOfPlayer].getHits();
    }

    public int getNumberOfMisses(int numOfPlayer){
        return players[numOfPlayer].getMisses();
    }

    public double getAvgTime(int numOfPlayer){
        return players[numOfPlayer].getAvgTimeOfTurn();
    }

    public int getBoardSize() {
        return boardSize;
    }
    public int getNumOfPlayer() {
        return numOfPlayer;
    }

    public long[] GetTimePass(){
        long curTime = java.time.Instant.now().getEpochSecond();
        long timePass = curTime - startGameTime;
        long minutes = timePass / 60;
        long seconds = timePass % 60;
        long[] result = new long[2];
        result[0] = minutes;
        result[1] = seconds;
        return  result;
    }

    public Board[] getMyBoards(int numOfPlayer){
        Board [] myBoards = new Board[2];
        myBoards[0] = players[numOfPlayer].getMyBoard();
        myBoards[1] = players[numOfPlayer].getTryingBoard();
        return myBoards;
    }

    public int getScore(int numOfPlayer){
        return players[numOfPlayer].getScore();
    }

    public int getGameMode() {
        return gameMode;
    }
}
