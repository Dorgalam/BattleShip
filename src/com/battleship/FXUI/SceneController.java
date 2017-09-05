package com.battleship.FXUI;

import com.battleship.Logic.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/resources/scenes/Scene.fxml"));
            Parent gameTabs = FXMLLoader.load(getClass().getResource("/resources/scenes/GameTabs.fxml"));
            Scene scene = new Scene(root, 400, 400);
            stage.setScene(scene);
            stage.show();
            Context.getInstance().getGameStartedHandler().selectedProperty().addListener((observable, oldValue, newValue) -> {
                double windowSize = Context.getInstance().getWindowSize();
                stage.close();
                stage.setScene(new Scene(gameTabs, windowSize , windowSize));
                stage.show();
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
