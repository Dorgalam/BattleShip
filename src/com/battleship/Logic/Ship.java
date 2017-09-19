package com.battleship.Logic;

public class Ship {
    private int count;
    private int score;


    private int dir;
    private Point[] loc;

    public Ship(Ship toClone) {
        this.count = toClone.count;
        this.score = toClone.score;
        this.dir = toClone.dir;
        this.loc = toClone.loc.clone();
    }

    public int getDir() {
        return dir;
    }

    Ship(int length, int dir, int x, int y, int score) {
        x--;
        y--;
        this.score = score;
        this.count = length;
        this.dir = dir;
        int i;

        switch (dir) {
            case Board.ROW:
                this.loc = new Point[length];
                for (i = 0; i < length; i++) {
                    loc[i] = new Point(x + i, y);
                }
                break;
            case Board.COL:
                this.loc = new Point[length];
                for ( i = 0; i < length; i++) {
                    loc[i] = new Point(x , y+ i);
                }
                break;
            case Board.UP_RIGHT:
                this.count = length * 2 - 1;
                this.loc = new Point[length * 2 - 1];
                y = y + length - 1;
                for (i = 0; i < length - 1; ++i) {
                    loc[i] = new Point(x, y--);
                }
                for(int j = 0; j < length; ++j, ++i) {
                    loc[i] = new Point(x + j, y);
                }
                break;
            case Board.RIGHT_DOWN:
                this.count = length * 2 - 1;

                this.loc = new Point[length * 2 - 1];
                x = x - length + 1;
                for (i = 0; i < length - 1; ++i) {
                    loc[i] = new Point(x++, y);
                }
                for(int j = 0; j < length; ++j, ++i) {
                    loc[i] = new Point(x, y + j);
                }
                break;
            case Board.DOWN_RIGHT:
                this.count = length * 2 - 1;

                this.loc = new Point[length * 2 - 1];
                y = y - length + 1;
                for (i = 0; i < length - 1; ++i) {
                    loc[i] = new Point(x, y++);
                }
                for(int j = 0; j < length; ++j, ++i) {
                    loc[i] = new Point(x + j, y);
                }
                break;
            case Board.RIGHT_UP:
                this.count = length * 2 - 1;

                this.loc = new Point[length * 2 - 1];
                x = x - length + 1;
                for (i = 0; i < length - 1; ++i) {
                    loc[i] = new Point(x++, y);
                }
                for(int j = 0; j < length; ++j, ++i) {
                    loc[i] = new Point(x, y - j);
                }
                break;
            default:
                System.out.println("illegal?");
                break;
        }
    }

    public Point[] getLocation() {
        return loc;
    }

    public void setLocation(Point[] loc) {
        this.loc = loc;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() { return score; }

    public int getCount() {
        return count;
    }

    void decCount() { this.count--; }

}