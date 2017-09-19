package com.battleship.FXUI;

import com.battleship.Logic.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class GameEndedController {
    @FXML Button newGameButton;
    @FXML Button rewindButton;
    @FXML Text winningPlayer;
    @FXML Text playerOne;
    @FXML Text playerTwo;
    @FXML Text scoreOne;
    @FXML Text scoreTwo;


    private Context instance = Context.getInstance();
    private Game battleships = instance.getBattleShipGame();


    @FXML
    protected void initialize() {
        System.out.println(instance.getWinningPlayerName());
        winningPlayer.setText(instance.getWinningPlayerName());
        playerOne.setText(battleships.getPlayerName(0));
        playerTwo.setText(battleships.getPlayerName(1));
        scoreOne.setText(String.valueOf(battleships.getScore(0)));
        scoreTwo.setText(String.valueOf(battleships.getScore(1)));
    }

    @FXML
    void rewindGame() {
        instance.setRewindMode(true);
        battleships.restartCurrentPlayer();
        instance.setTurnOutcome(battleships.nextPlayer());
        try {
            instance.getGameController().startNewGame();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void startNewGame() {
        instance.getShowNewGameDialog().setSelected(true);
    }

}
