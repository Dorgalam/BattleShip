package com.battleship.FXUI;

import com.battleship.Logic.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.swing.*;

public class SceneController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Context instance = Context.getInstance();

    private Parent gameTabs;
    private Stage stage;


    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;
        gameTabs = FXMLLoader.load(getClass().getResource("/resources/scenes/GameTabs.fxml"));
        showStartingScreen();
        Context.getInstance().setGameController(this);
        Context.getInstance().getGameStartedHandler().selectedProperty().addListener((observable, oldValue, shouldStartNew) -> {
            try {
                if (shouldStartNew) {
                    startNewGame();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        Context.getInstance().getShowNewGameDialog().selectedProperty().addListener((observable, oldValue, newValue) ->  {
            if (newValue) {
                try {
                    showStartingScreen();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }


    void showStartingScreen() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/scenes/Scene.fxml"));
        if (stage.getScene() == null) {
            stage.setScene(new Scene(root));
        } else {
            stage.getScene().setRoot(root);
        }
        stage.setHeight(400);
        stage.setWidth(400);
        stage.show();
    }

    void startNewGame() throws Exception {
        double windowSize = Context.getInstance().getWindowSize();
        stage.getScene().setRoot(gameTabs);
        gameTabs.setVisible(true);
        Context.getInstance().getGameTabsController().gameStarted();
        String title = (Context.getInstance().getBattleShipGame().getGameMode() == Game.ADVANCED ? "Advanced" : "Basic") + " Battleships";
        stage.setTitle(title);
        stage.setHeight(windowSize);
        stage.setWidth(windowSize);
        instance.getGameStartedHandler().setSelected(true);
        gameTabs = FXMLLoader.load(getClass().getResource("/resources/scenes/GameTabs.fxml"));
    }
    void gameEnded(){
        try {
            Parent gameEnded = FXMLLoader.load(getClass().getResource("/resources/scenes/GameEnded.fxml"));
            stage.getScene().setRoot(gameEnded);
            stage.setHeight(500);
            stage.setWidth(400);
            stage.setTitle("Game finished!");
            instance.getGameStartedHandler().setSelected(false);

        } catch (Exception e ){
            System.out.println(e.getMessage());
        }
    }
}
