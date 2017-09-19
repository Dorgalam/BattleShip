package com.battleship.FXUI;

import com.battleship.Logic.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SceneController extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    private Parent gameTabs;
    private Stage stage;


    @Override
    public void start(Stage stage) {

        try {
            gameTabs = FXMLLoader.load(getClass().getResource("/resources/scenes/GameTabs.fxml"));
            this.stage = stage;
            showStartingScreen();
            Context.getInstance().getGameStartedHandler().selectedProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    if(newValue) {
                        startNewGame();
                    } else {
                        gameEnded();
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void showStartingScreen() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/scenes/Scene.fxml"));
        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    void startNewGame() throws Exception {
        double windowSize = Context.getInstance().getWindowSize();
        String title = (Context.getInstance().getBattleShipGame().getGameMode() == Game.ADVANCED ? "Advanced" : "Basic") + " Battleships";
        stage.setTitle(title);
        stage.getScene().setRoot(gameTabs);
        stage.setHeight(windowSize);
        stage.setWidth(windowSize);
    }
    void gameEnded() throws Exception {
        Parent gameEnded = FXMLLoader.load(getClass().getResource("/resources/scenes/GameEnded.fxml"));
        gameTabs = FXMLLoader.load(getClass().getResource("/resources/scenes/GameTabs.fxml"));
        stage.getScene().setRoot(gameEnded);
        stage.setHeight(500);
        stage.setWidth(400);
        stage.setTitle("Game finished!");
    }
}
