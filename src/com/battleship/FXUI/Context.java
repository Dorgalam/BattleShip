package com.battleship.FXUI;

import com.battleship.Logic.Game;
import javafx.scene.control.CheckBox;

public class Context {

    private CheckBox gameStartedHandler = new CheckBox();

    CheckBox getGameStartedHandler() {
        return gameStartedHandler;
    }

    private Game BattleShipGame;

    void setBattleShipGame(Game battleShipGame) {
        BattleShipGame = battleShipGame;
    }

    Game getBattleShipGame() {
        return BattleShipGame;
    }

    void startGame() {
        gameStartedHandler = new CheckBox();
        gameStartedHandler.setSelected(true);
    }

    private static Context ourInstance = new Context();

    public static Context getInstance() {
        return ourInstance;
    }

    private Context() {
    }
}
