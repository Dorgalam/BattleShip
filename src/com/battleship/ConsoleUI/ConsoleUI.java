package com.battleship.ConsoleUI;

import com.battleship.Logic.Game;
import com.battleship.Logic.GameException;

public class ConsoleUI {
    private Game gameLogic;
    public void startGame() {
        boolean valid = false;
        System.out.println("Welcome to Lior and Dor's BattleShip!");
        while (!valid) {
            try {
                System.out.println("Please write down your XML's location so we can begin (c:/DIR/DIR/data.xml)");
                gameLogic = new Game("src/resources/battleShip_5_basic.xml");
                valid = true;
            } catch (GameException e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again.");
            }
        }
    }


}
