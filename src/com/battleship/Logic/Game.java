package com.battleship.Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

public class Game {
    private long startGameTime = 0;
    private Player[] players;
    private long startTurnTime = 0;
    private int boardSize;
    private int numOfPlayer = 0;
    private String playerNames[];
    private PlayerHistory history = new PlayerHistory();
    private int gameMode;
    public static final int BASIC = 0;
    public static final int ADVANCED = 1;

    private HashMap<Integer, String> turnEndingTable = new HashMap<Integer, String>() {
        {
            put(3, "Game finished");
            put(2, "Ship destroyed");
            put(1, "Hit");
            put(0, "Miss");
            put(-2, "Mine placed");
            put(-3, "Player quit");
        }
    };

    public Game(String xmlPath) throws Exception {
        try {
            this.startGameTime = java.time.Instant.now().getEpochSecond();
            this.players = new Player[ 2 ];
            BattleShipParser parser = new BattleShipParser(xmlPath);
            this.boardSize = parser.getBoardSize();
            this.gameMode = parser.getGameType().equals("BASIC") ? BASIC : ADVANCED;
            this.players[0] = new Player(boardSize, parser.getBoardAShips(), parser);
            this.players[1] = new Player(boardSize, parser.getBoardBShips(), parser);
        }
        catch (GameException ex){
            throw ex;
        }
    }


    public String[] getPlayerNames() {
        return playerNames;
    }

    public void updatePlayers() {
        history.logPlayer(players);
        history.pushToPlayerSequence(numOfPlayer);
    }

    public void updateTurnEndStatus(int type) {
        history.logEnding(turnEndingTable.get(type));
    }

    public boolean isValidPlaceForMine(int x, int y) {
        return players[numOfPlayer].checkMineLoc(new Point(x,y)) && players[1 - numOfPlayer].getTryingBoard().getSquare(x,y) != Board.MISS;
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
        updatePlayers();
        if (players[numOfPlayer].isAlreadyChecked(p)) {
            if(players[1 - numOfPlayer].getMyBoard().getSquare(p) != Board.MINE)
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
        } else if (res == -2) {
            mineHit(x, y);
        }
        if (res >= 2) {
            addScore(res - 2);
        }
        updateTurnEndStatus(res >= 2 ? 2 : res);
        return res; // 2 = ship down ,  1 = HIT , 0 = MISS , -1 = already checked
    }


    public void mineHit(int x, int y) {
        int temp = numOfPlayer;
        numOfPlayer = 1 - numOfPlayer;
        makeTurn(x,y);
        numOfPlayer = 1 - temp;
    }

    public int putMine(int x, int y) {
        if (players[numOfPlayer].isMinesLeft()) {
            if (players[numOfPlayer].isValidPlaceForMine(new Point(x,y))) {
                numOfPlayer = 1 - numOfPlayer;
                updatePlayers();
                updateTurnEndStatus(-2);
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
        boolean finished = players[1 - numOfPlayer].allShipsDown();
        if (finished) {
            updatePlayers();
            updateTurnEndStatus(3);
        }
        return finished;
    }

    public int getNumOfTurns(){
        return players[0].getNumberOfTurns() + players[1].getNumberOfTurns();
    }

    public int getNumberOfHits(int numOfPlayer){
        return players[numOfPlayer].getHits();
    }
    public int getNumberOfHits(){
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

    public void restartCurrentPlayer() {
        numOfPlayer = 0;
    }

    public String nextPlayer() {
        this.numOfPlayer = history.getNextPlayerNum();
        this.players = history.getNextPlayer();
        return history.getTurnEnding();
    }

    public String prevPlayer() {
        this.numOfPlayer = history.getPrevPlayerNum();
        this.players = history.getPrevPlayer();
        return history.getTurnEnding();
    }

    public int getTurnsListLength() {
        return history.getTurnsListLength();
    }

    public int getPlayerIterator() {
        return history.getPlayerIterator();
    }

    public int getScore(){
        return players[numOfPlayer].getScore();
    }
    public int getScore(int numOfPlayer){
        return players[numOfPlayer].getScore();
    }
    void addScore(int score) {
        players[numOfPlayer].addScore(score);
    }

    public int getGameMode() {
        return gameMode;
    }

}

class PlayerHistory {
    private int playerIterator = 0;
    private int numIterator = 0;
    private ArrayList<Player[]> playerObject = new ArrayList<>();
    private ArrayList<Integer> playerNumSequence = new ArrayList<>();

    private ArrayList<String> turnEndings = new ArrayList<>();

    int getTurnsListLength() {
        return playerObject.size();
    }

    int getPlayerIterator() {
        return playerIterator;
    }

    void logEnding(String ending) {
        turnEndings.add(ending);
    }

    String getTurnEnding() {
        return turnEndings.get(playerIterator - 1);
    }

    void logPlayer(Player[] player) {
        Player players[] = new Player[2];
        players[0] = new Player(player[0]);
        players[1] = new Player(player[1]);
        playerObject.add(players);
    }
    void pushToPlayerSequence(int num) {
        playerNumSequence.add(num);
    }
    Player[] getNextPlayer() {
        return playerObject.get(playerIterator++);
    }
    int getNextPlayerNum() {
        return playerNumSequence.get(numIterator++);
    }
    Player[] getPrevPlayer() {
        playerIterator -= 2;
        return playerObject.get(playerIterator++);
    }

    int getPrevPlayerNum() {
        numIterator -= 2;
        return playerNumSequence.get(numIterator++);
    }
}