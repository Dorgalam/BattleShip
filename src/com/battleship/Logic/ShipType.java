package com.battleship.Logic;

class ShipType {

    private int length;
    private int score;
    private int amount;
    private String shipType;
    private String category;

    ShipType(int length, int score, int amount, String category) {
        this.length = length;
        this.score = score;
        this.category = category;
        this.amount = amount;
    }

    public Ship[] getShipArr() {
        return shipArr;
    }

    private Ship shipArr[];
    int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



}