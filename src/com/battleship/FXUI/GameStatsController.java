package com.battleship.FXUI;

import com.battleship.Logic.Game;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class GameStatsController {
    @FXML Text playerOne;
    @FXML Text playerTwo;
    @FXML Text scoreOne;
    @FXML Text scoreTwo;
    @FXML Text missesOne;
    @FXML Text missesTwo;
    @FXML Text timeOne;
    @FXML Text timeTwo;
    @FXML Text turns;
    @FXML Text timePassed;
    @FXML Pane gameStats;

    Game game;

    @FXML
    void initialize() {
        Context.getInstance().getGameStartedHandler().selectedProperty().addListener((observable, oldValue, gameStarted) -> {
            if (gameStarted) {
                game = Context.getInstance().getBattleShipGame();
            }
        });
        gameStats.opacityProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue.equals(0.0)) {
                getStats();
            }
        });
    }
    private void getStats() {
        long [] time =  game.GetTimePass();
        turns.setText(game.getNumOfTurns() + "");
        timePassed.setText(time[0] + ":" + time[1]);
        playerOne.setText(game.getPlayerName(0));
        playerTwo.setText(game.getPlayerName(1));
        scoreOne.setText(String.valueOf(game.getScore(0)));
        scoreTwo.setText(String.valueOf(game.getScore(1)));
        missesOne.setText(String.valueOf(game.getNumberOfMisses(0)));
        missesTwo.setText(String.valueOf(game.getNumberOfMisses(1)));
        timeOne.setText(String.valueOf(game.getAvgTime(0)));
        timeTwo.setText(String.valueOf(game.getAvgTime(1)));
    }

}

