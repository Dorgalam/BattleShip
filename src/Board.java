public class Board {
    public static final int EMPTY_CELL = -1;
    public static final int MINE = -2;
    public static final int HIT = -3;
    public static final int MISS = -4;
    private static final int ROW = 0;
    private int[][] matrix;
    private int matrixSize;

    public Board(int size) { //empty board - enemy board
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

    public Board(int size , int[] x , int[] y , int[] shipLen , int[] direction) throws Exception{ //my board
        this(size);
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < shipLen[i]; j++) {
                try {
                    matrix[x[i]][y[i]] = i;
                    if (direction[i] == ROW)
                        y[i]++;
                    else
                        x[i]++;
                }
                catch (Exception ex)
                {// out of range exception
                    throw ex;
                }
            }
        }
    }

    public int getSize() {
        return matrixSize;
    }

    public boolean isHit(int x, int y) {
        if(matrix[x][y] >= 0 || matrix[x][y] == MINE) {
            return true;
        }
        return false;
    }

    public int getSquare(int x,int y) {
        return matrix[x][y];
    }

    public boolean isValidBoard(int shipsSquares) {
        int countShipsSquares = 0;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (matrix[i][j] >= 0) { //is ship
                    countShipsSquares++;
                    if (!isValidPlace(i, j, matrix[i][j])) // check if this ship doesn't collide with other ships
                        return false;
                }
            }
        }
        return countShipsSquares == shipsSquares;
    }

    public boolean isValidPlace(int x , int y, int validType) {
        for (int i = x - 1; i < x + 1; i++) {
            for (int j = y - 1; j < y + 1; j++) {
                if (!(i < 0 || i > matrixSize || j < 0 || j > matrixSize)) {
                    if (matrix[i][j] != validType && matrix[i][j] != EMPTY_CELL) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void addMine(int x , int y) {
        matrix[x][y] = MINE;
    }

    public void updateTheBoard(int x, int y , int n) {
        matrix[x][y] = n;
    }

    public boolean alreadyChecked(int x, int y) {
        if(matrix[x][y] == MISS || matrix[x][y] == HIT)
            return true;
        return false;
    }
}
