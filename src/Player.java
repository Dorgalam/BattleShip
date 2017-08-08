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

    public boolean isValidPlaceForMine(int x, int y){
        if (!myBoard.isValidPlace(x,y ,Board.EMPTY_CELL)) {
            return false;
        }
        myBoard.addMine(x, y);
        minesLeft--;
        return true;
    }

    public int checkHit(int x, int y) {
        if(myBoard.isHit(x,y)) {
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

    public boolean isAlreadyChecked(int x, int y) {
        if(tryingBoard.alreadyChecked(x,y))
            return true;
        return false;
    }
    public void incHit(int x, int y) {
        tryingBoard.updateTheBoard(x,y,Board.HIT);
        this.hits++;
    }

    public int getMisses() {
        return misses;
    }

    public void incMiss(int x, int y) {
        tryingBoard.updateTheBoard(x,y,Board.MISS);
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
