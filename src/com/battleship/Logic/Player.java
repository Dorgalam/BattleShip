package com.battleship.Logic;

class Player {
    private Board myBoard;
    private Board tryingBoard;
    private Ship[] myShips;
    private int numberOfTurns = 0;
    private int hits = 0;
    private int misses = 0;
    private double avgTimeOfTurn = 0;
    private int minesLeft = 2;

     Player(Board myBoard, Ship[] myShips) {
        this.myBoard = myBoard;
        this.tryingBoard = new Board(myBoard.getSize());
        this.myShips = myShips;
    }

    boolean isValidPlaceForMine(int x, int y){
        if (!myBoard.isValidPlace(x, y ,Board.EMPTY_CELL)) {
            return false;
        }
        myBoard.addMine(x, y);
        minesLeft--;
        return true;
    }

    int checkHit(int x, int y) {
        if(myBoard.isHit(x, y)) {
            if (myBoard.getSquare(x, y) != Board.MINE) {
                myShips[myBoard.getSquare(x, y)].decCount();
                if (myShips[myBoard.getSquare(x, y)].getCount() == 0) {
                    int score = myShips[myBoard.getSquare(x, y)].getPoints();
                    myBoard.updateTheBoard(x,y,Board.HIT);
                    return 2 + score;
                }
                myBoard.updateTheBoard(x,y,Board.HIT);
                return 1;
            }
            else {
                myBoard.updateTheBoard(x,y,Board.MISS);
                return -1; /* MINE HIT */
            }
        }
        else{
            return 0;
        }
    }

    boolean allShipsDown(){
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

    boolean isAlreadyChecked(int x, int y) {
        return (tryingBoard.alreadyChecked(x,y));
    }
    void incHit(int x, int y) {
        tryingBoard.updateTheBoard(x,y,Board.HIT);
        this.hits++;
    }

    int getMisses() {
        return misses;
    }

    void incMiss(int x, int y) {
        tryingBoard.updateTheBoard(x,y,Board.MISS);
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
}
