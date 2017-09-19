package com.battleship.FXUI;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class GameTabsController {
    @FXML
    TabPane gameTabs;

    @FXML
    Pane opponentsGrid;

    @FXML
    Pane yourGrid;

    @FXML
    Pane gameStats;

    private Context instance = Context.getInstance();

    @FXML
    protected void initialize() {
        instance.setGameTabsController(this);
        instance.getGameStartedHandler().selectedProperty().addListener((observable, oldValue, started) -> {
            if(started) {
                gameStarted();
            }
        });
        gameTabs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, tab) -> {
            TransitionEffects.fadeEffect(getNodeFromTab(tab.getId()), getNodeFromTab(oldValue.getId()));
        });
    }

    void gameStarted() {
        gameTabs.setVisible(true);
        TransitionEffects.fadeEffect(opponentsGrid, null);
    }


    Node getNodeFromTab(String tabId) {
        switch (tabId) {
            case "0":
                return opponentsGrid;
            case "1":
                return yourGrid;
            case "2":
                return gameStats;
            default:
                return null;
        }
    }
}
