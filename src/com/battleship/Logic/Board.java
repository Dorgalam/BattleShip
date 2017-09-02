package com.battleship.Logic;

public class Board {
    public static final int EMPTY_CELL = -1;
    public static final int MINE = -2;
    public static final int HIT = -3;
    public static final int MISS = -4;
    static final int ROW = 0;
    private int[][] matrix;
    private int matrixSize;

    Board(int size) { //empty board - enemy board
        this.matrixSize = size;
        matrix = new int[matrixSize][];
        for (int i = 0 ;i < matrixSize ;i++)
        {
            matrix[i] = new int[matrixSize];
        }
        for (int i = 0 ;i < matrixSize ;i++)
        {
            for(int j = 0 ;j < matrixSize ;j++)
            {
                matrix[i][j] = EMPTY_CELL;
            }
        }
    }

    public Board(int size ,Ship[] ships) throws GameException { //my board
        this(size);
        int shipSquares = 0;
        Point[] pArray = new Point[ships.length];
        for (int i = 0; i < ships.length; i++) {
            pArray = ships[i].getLocation();
            for (int j = 0; j < ships[i].getCount(); j++) {
                try {
                    matrix[pArray[j].getX()][pArray[j].getY()] = i;
                } catch (Exception exc) {// out of range exception
                    GameException ex = new GameException(exc.getMessage());
                    ex.setMsg(String.format("This Board isn't valid, you tried to put a ship in a place(%d,%d) that doesn't exist" +
                            " in the board with squares between (1,1) to(%d,%d)", pArray[j].getX() + 1, pArray[j].getY() + 1, matrixSize, matrixSize));
                    throw ex;
                }
            }
        }
        for (Ship s:ships) {
            shipSquares+= s.getCount();
        }
        try {
            if (!isValidBoard(shipSquares))
                throw new GameException();
        }
        catch (GameException ex){
            throw ex;
        }
    }

    public int getSize() {
        return matrixSize;
    }

    public boolean isHit(Point p) {
        if (matrix[ p.getX() ][ p.getY() ] >= 0 || matrix[ p.getX() ][ p.getY() ] == MINE) {
            return true;
        }
        return false;
    }

    public int getSquare(Point p) {
        return matrix[p.getX()][p.getY()];
    }

    public int getSquare(int x, int y) {
        return matrix[x][y];
    }

    public boolean isValidBoard(int shipsSquares) throws GameException {
        int countShipsSquares = 0;
        try {
            for (int i = 0; i < matrixSize; i++) {
                for (int j = 0; j < matrixSize; j++) {
                    if (matrix[ i ][ j ] >= 0) { //is ship
                        countShipsSquares++;
                        if (!isValidPlace(new Point(i,j), matrix[ i ][ j ])) // check if this ship doesn't collide with other ships
                            return false;
                    }
                }
            }
        } catch (GameException ex) {// out of range exception
            ex.setMsg(String.format("This Board isn't valid, you tried to put a ship in a place(%d,%d)," +
                    " that doesn't exist in the board",ex.getX(),ex.getY()));
            throw ex;
        }
        return countShipsSquares == shipsSquares;
    }

    public boolean isValidPlace(Point p, int validType) throws GameException {
        int x = p.getX();
        int y = p.getY();
        try {
            if (matrix[x][y] != EMPTY_CELL && matrix[x][y] != validType)
                return false;
        } catch (Exception exc) {// out of range exception
            GameException ex = new GameException(exc.getMessage());
            ex.setMsg(String.format("you tried to attack in a place (%d,%d) that doesn't exist in the board," +
                    "valid input is between (1,1) to (%d,%d)", x + 1, y + 1, matrixSize,matrixSize), x + 1, y + 1);
            throw ex;
        }
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (!(i < 0 || i >= matrixSize || j < 0 || j >= matrixSize)) {
                    if (matrix[i][j] != validType && matrix[i][j] != EMPTY_CELL) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void addMine(Point p) {
        matrix[p.getX()][p.getY()] = MINE;
    }

    public void updateTheBoard(Point p, int n) {
        matrix[p.getX()][p.getY()] = n;
    }

    public boolean alreadyChecked(Point p) {
        if(matrix[p.getX()][p.getY()] == MISS || matrix[p.getX()][p.getY()] == HIT)
            return true;
        return false;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int[] getLine(int i) {
        return matrix[i];
    }
}