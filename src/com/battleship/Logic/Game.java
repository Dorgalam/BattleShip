package com.battleship.Logic;

public class Game {
    private long startGameTime = 0;
    private Player[] players;
    private long startTurnTime = 0;

    public Game(Player player1, Player player2) {
        this.startGameTime = java.time.Instant.now().getEpochSecond();
        this.players = new Player[2];
        this.players[0] = player1;
        this.players[1] = player2;
    }

    public void startTurnClock(int numOfPlayer) {
        startTurnTime = java.time.Instant.now().getEpochSecond();
        players[numOfPlayer].incTurn();
    }

    public void endTurnClock(int numOfPlayer){
        long end = java.time.Instant.now().getEpochSecond();
        players[numOfPlayer].setAvgTimeOfTurn(end-startTurnTime);
    }

    public int makeTurn(int numOfPlayer, int x, int y) throws Exception
    {
        Point p = new Point(x,y);
        try {
            int res;
            if (players[numOfPlayer].isAlreadyChecked(p)) {
                res = -1;
            } else {
                res = players[1 - numOfPlayer].checkHit(p);
            }
            if (res >= 1) {
                players[numOfPlayer].incHit(p);
            } else if (res == 0) {
                players[numOfPlayer].incMiss(p);
            }
            return res; // 2 = ship down ,  1 = HIT , 0 = MISS , -1 = already checked
        }
        catch (Exception ex) {
            throw ex;
        }
    }

    public int putMine(int numOfPlayer, int x, int y) throws GameException{
        if (players[numOfPlayer].isMinesLeft()) {
            try {
                if (players[numOfPlayer].isValidPlaceForMine(new Point(x,y)))
                    return 1; // valid place + mines left
                return 2; // mines left + is not valid place
            } catch (GameException ex) {
                throw ex;
            }
        }
        return 0; // there are no mines left
    }

    public boolean isGameFinished(int numOfPlayer){
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
}
