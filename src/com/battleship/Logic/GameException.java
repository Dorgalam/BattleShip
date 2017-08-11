package com.battleship.Logic;

public class GameException extends Exception {
    private String msg;
    private int x = 0;
    private int y = 0;

    GameException(){}

    GameException(String msg){
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }

    void setMsg(String ms) {
        msg = ms;
    }

    void setMsg(String ms, int index1 , int index2) {
        x = index1;
        y = index2;
        setMsg(ms);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
