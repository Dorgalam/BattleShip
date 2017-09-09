package com.battleship.FXUI;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GameTabsController {
    @FXML
    TabPane gameTabs;


    @FXML
    Pane opponentsGrid;

    @FXML
    Pane yourGrid;

    @FXML
    Pane gameStats;


    @FXML
    protected void initialize() {
        Context.getInstance().getGameStartedHandler().selectedProperty().addListener((observable, oldValue, started) -> {
            if(started) {
                gameTabs.setVisible(true);
                TransitionEffects.fadeEffect(opponentsGrid, null);
            }
        });
        Context.getInstance().setGameTabs(gameTabs);
        gameTabs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, tab) -> {
            TransitionEffects.fadeEffect(getNodeFromTab(tab.getId()), getNodeFromTab(oldValue.getId()));
        });
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
