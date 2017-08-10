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

    public Player(Board myBoard, Ship[] myShips) {
        this.myBoard = myBoard;
        this.tryingBoard = new Board(myBoard.getSize());
        this.myShips = myShips;
    }

    public boolean isValidPlaceForMine(Point point)throws GameException {
        try {
            if (!myBoard.isValidPlace(point, Board.EMPTY_CELL)) {
                return false;
            }
        } catch (GameException ex) {// out of range exception
            ex.setMsg(String.format("you tried to put a mine in a place(%d,%d) that doesn't exist in the board valid input is" +
                    " between (1,1) to (%d,%d)", point.getX() + 1, point.getY() + 1, myBoard.getSize(), myBoard.getSize()));
            throw ex;
        }
        myBoard.addMine(point);
        minesLeft--;
        return true;
    }

    public int checkHit(Point point) throws Exception{
        try {
            if (myBoard.isHit(point)) {
                if (myBoard.getSquare(point) != Board.MINE) {
                    myShips[myBoard.getSquare(point)].decCount();
                    if (myShips[myBoard.getSquare(point)].getCount() == 0) {
                        int score = myShips[myBoard.getSquare(point)].getScore();
                        myBoard.updateTheBoard(point, Board.HIT);
                        return 2 + score;
                    }
                    myBoard.updateTheBoard(point, Board.HIT);
                    return 1;
                } else {
                    myBoard.updateTheBoard(point, Board.MISS);
                    return -1; /* MINE HIT */
                }
            } else {
                return 0;
            }
        }
        catch (Exception ex){
            throw ex;
        }
    }

    public boolean allShipsDown(){
        for (Ship s:myShips) {
            if(s.getCount() > 0)
                return false;
        }
        return true;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void incTurn() {
        this.numberOfTurns++;
    }

    public int getHits() {
        return hits;
    }

    public boolean isAlreadyChecked(Point point) throws Exception {
        try {
            if (tryingBoard.alreadyChecked(point))
                return true;
            return false;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void incHit(Point point) {
        tryingBoard.updateTheBoard(point,Board.HIT);
        this.hits++;
    }

    public int getMisses() {
        return misses;
    }

    public void incMiss(Point point) {
        tryingBoard.updateTheBoard(point,Board.MISS);
        this.misses++;
    }

    public double getAvgTimeOfTurn() {
        return avgTimeOfTurn;
    }

    public void setAvgTimeOfTurn(long timeOfTurn) {
        this.avgTimeOfTurn = (this.avgTimeOfTurn*(numberOfTurns-1) + timeOfTurn) / numberOfTurns;
    }

    public boolean isMinesLeft() {
        return minesLeft > 0;
    }
}