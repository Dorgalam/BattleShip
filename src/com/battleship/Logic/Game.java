package com.battleship.Logic;

public class Game {
    private long startGameTime = 0;
    private Player[] players;
    private static long startTurnTime = 0;

     Game(Player player1, Player player2) {
        this.startGameTime = java.time.Instant.now().getEpochSecond();
        this.players = new Player[2];
        this.players[0] = player1;
        this.players[1] = player2;
    }

     static void startTurnClock(){
        startTurnTime =  java.time.Instant.now().getEpochSecond();
    }

     void endTurnClock(int numOfPlayer){
        long end = java.time.Instant.now().getEpochSecond();
        players[numOfPlayer].setAvgTimeOfTurn(end-startTurnTime);
    }

     int makeTurn(int numOfPlayer, int x, int y)
    {
        int res;
        players[numOfPlayer].incTurn();
        if(players[numOfPlayer].isAlreadyChecked(x,y)) {
            res = -1;
        }
        else {
            res = players[1 - numOfPlayer].checkHit(x, y);
        }
        if(res >= 1){
            players[numOfPlayer].incHit(x,y);
        }
        else if(res == 0) {
            players[numOfPlayer].incMiss(x, y);
        }
        return  res; // 2 = ship down ,  1 = HIT , 0 = MISS , -1 = already checked
    }

    int putMine(int numOfPlayer, int x, int y) {
        players[numOfPlayer].incTurn();
        if (players[numOfPlayer].isMinesLeft()) {
            if (players[numOfPlayer].isValidPlaceForMine(x, y))
                return 1; // valid place + mines left
            return 2; // mines left + is not valid place
        }
        return 0; // there are no mines left
    }

    boolean isGameFinished(int numOfPlayer){
        return players[1 - numOfPlayer].allShipsDown();
    }

    public int getNumOfTurns(){
        return players[0].getNumberOfTurns() + players[1].getNumberOfTurns();
    }

    int getNumberOfHits(int numOfPlayer){
        return players[numOfPlayer].getHits();
    }

    public int getNumberOfMisses(int numOfPlayer){
        return players[numOfPlayer].getMisses();
    }

    double getAvgTime(int numOfPlayer){
        return players[numOfPlayer].getAvgTimeOfTurn();
    }

    long[] GetTimePass(){
        long curTime = java.time.Instant.now().getEpochSecond();
        long timePass = curTime - startGameTime;
        long minutes = timePass / 60;
        long seconds = timePass % 60;
        long[] result = new long[2];
        result[0] = minutes;
        result[1] = seconds;
        return  result;
    }
}
