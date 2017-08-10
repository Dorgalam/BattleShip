package com.battleship.Logic;

class Ship {
    private int count;

    public Point[] getLoc() {
        return loc;
    }

    public void setLoc(Point[] loc) {
        this.loc = loc;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {

        return score;
    }

    private int score;

    private Point[] loc;
    Ship(int length, int dir, int x, int y, int score) {
        this.score = score;
        this.count = length;
        this.loc = new Point[length];
        switch (dir) {
            case Board.ROW:
                for (int i = 0; i < length; i++) {
                    loc[i] = new Point(x, y + i);
                }
                break;
            default:
                for (int i = 0; i < length; i++) {
                    loc[i] = new Point(x + i, y);
                }
                break;
        }
    }

    int getCount() {
        return count;
    }

    void decCount() {
        this.count--;
    }

}