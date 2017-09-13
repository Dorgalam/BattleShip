package com.battleship.FXUI;

import com.battleship.Logic.Game;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TabPane;

public class Context {

    private CheckBox gameStartedHandler = new CheckBox();

    private double windowSize;

    public TabPane getGameTabs() {
        return gameTabs;
    }

    public void setGameTabs(TabPane gameTabs) {
        this.gameTabs = gameTabs;
    }

    TabPane gameTabs;

    CheckBox getGameStartedHandler() {
        return gameStartedHandler;
    }

    private Game BattleShipGame;

    void setBattleShipGame(Game battleShipGame) {
        BattleShipGame = battleShipGame;
        int boardSize = battleShipGame.getBoardSize();
        windowSize = boardSize  < 9 ? 420 : boardSize * 32 * 1.5;
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

    public double getWindowSize() {
        return windowSize;
    }
}
