package com.battleship.Logic;

public class Player {
    private Board myBoard;
    private Board tryingBoard;
    private Ship[] myShips;
    private int numberOfTurns = 0;
    private int hits = 0;
    private int misses = 0;
    private double avgTimeOfTurn = 0;
    private int minesLeft = 2;
    private int score = 0;

    public Player(Board myBoard, Ship[] myShip) {
        this.myBoard = myBoard;
        this.tryingBoard = new Board(myBoard.getSize());
        this.myShips = myShips;
    }

    Player(int boardSize, Ship[] myShips) throws GameException{
        try {
            this.myBoard = new Board(boardSize, myShips);
            this.tryingBoard = new Board(boardSize);
            this.myShips = myShips;
        } catch (Exception e) {
            GameException ex = new GameException();
            ex.setMsg("Board details in this xml are invalid, please try another XML");
            throw ex;
        }
    }

    boolean isValidPlaceForMine(Point point) {
        try {
            if (!myBoard.isValidPlace(point, Board.MINE)) {
                return false;
            }
        } catch (GameException e) {
            return false;
        }
        myBoard.addMine(point);
        minesLeft--;
        return true;
    }

    public Ship[] getMyShips() {
        return myShips;
    }

    int checkHit(Point point) {
        if (myBoard.isHit(point)) {
            if (myBoard.getSquare(point) != Board.MINE) {
                myShips[ myBoard.getSquare(point) ].decCount();
                if (myShips[ myBoard.getSquare(point) ].getCount() == 0) {
                    int score = myShips[ myBoard.getSquare(point) ].getScore();
                    myBoard.updateTheBoard(point, Board.HIT);
                    return 2 + score;
                }
                myBoard.updateTheBoard(point, Board.HIT);
                return 1;
            } else {
                myBoard.updateTheBoard(point, Board.MISS);
                return -2; /* MINE HIT */
            }
        } else {
            return 0;
        }
    }

    public void decTurns() { this.numberOfTurns--; }

    public boolean allShipsDown(){
        for (Ship s:myShips) {
            if(s.getCount() > 0)
                return false;
        }
        return true;
    }

    int getNumberOfTurns() {
        return numberOfTurns;
    }

    void incTurn() {
        this.numberOfTurns++;
    }

    int getHits() {
        return hits;
    }

    boolean isAlreadyChecked(Point point) {
        if (tryingBoard.alreadyChecked(point))
            return true;
        return false;
    }

    void incHit(Point point) {
        tryingBoard.updateTheBoard(point,Board.HIT);
        this.hits++;
    }

    int getMisses() {
        return misses;
    }

    void incMiss(Point point) {
        tryingBoard.updateTheBoard(point,Board.MISS);
        this.misses++;
    }

    double getAvgTimeOfTurn() {
        return avgTimeOfTurn;
    }

    void setAvgTimeOfTurn(long timeOfTurn) {
        this.avgTimeOfTurn = (this.avgTimeOfTurn*(numberOfTurns-1) + timeOfTurn) / numberOfTurns;
    }

     boolean isMinesLeft() {
        return minesLeft > 0;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public Board getMyBoard() {
        return myBoard;
    }

    public Board getTryingBoard() { return tryingBoard; }
}
