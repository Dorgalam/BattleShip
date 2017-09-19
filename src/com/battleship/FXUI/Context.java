package com.battleship.FXUI;

import com.battleship.Logic.Game;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TabPane;

public class Context {

    private  String winningPlayerName;

    private CheckBox gameStartedHandler = new CheckBox();

    private CheckBox showNewGameDialog = new CheckBox();

    private boolean rewindMode = false;

    private String turnOutcome;

    private String currentXmlPath;

    public void setTurnOutcome(String turnOutcome) {
        this.turnOutcome = turnOutcome;
    }

    public String getTurnOutcome() {
        return turnOutcome;
    }

    private double windowSize;

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    private int theme;


    private String playerNames[];


    void setPlayerNames(String names[]) {
        playerNames = names;
    }

    String[] getPlayerNames() {
        return playerNames;
    }

    public GameTabsController getGameTabsController() {
        return gameTabsController;
    }

    public void setGameTabsController(GameTabsController gameTabsController) {
        this.gameTabsController = gameTabsController;
    }

    GameTabsController gameTabsController;


    TabPane getGameTabs() {
        return gameTabs;
    }

    void setGameTabs(TabPane gameTabs) {
        this.gameTabs = gameTabs;
    }

    public SceneController getGameController() {
        return gameController;
    }

    public void setGameController(SceneController gameController) {
        this.gameController = gameController;
    }

    SceneController gameController;



    private TabPane gameTabs;

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

    public String getWinningPlayerName() {
        return winningPlayerName;
    }

    public void setWinningPlayerName(String winningPlayerName) {
        this.winningPlayerName = winningPlayerName;
    }

    public CheckBox getShowNewGameDialog() {
        return showNewGameDialog;
    }

    public void setShowNewGameDialog(CheckBox showNewGameDialog) {
        this.showNewGameDialog = showNewGameDialog;
    }

    public boolean isRewindMode() {
        return rewindMode;
    }



    public void setRewindMode(boolean rewindMode) {
        this.rewindMode = rewindMode;
    }

    public String getCurrentXmlPath() {
        return currentXmlPath;
    }

    public void setCurrentXmlPath(String currentXmlPath) {
        this.currentXmlPath = currentXmlPath;
    }
}
