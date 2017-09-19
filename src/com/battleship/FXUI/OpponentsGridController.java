package com.battleship.FXUI;

import com.battleship.Logic.Board;
import com.battleship.Logic.Game;
import com.battleship.Logic.Point;
import com.battleship.Logic.Ship;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;


public class OpponentsGridController extends GridBase {


    @FXML
    public Pane opponentsGrid;

    @FXML
    protected void initialize() {

        super.initialize();
        this.hitStyle = "hit-opponent";
        this.gridNum = 1;
        opponentsGrid.opacityProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue.equals(0.0)) {
                populateGrid();
            }
        });
    }



    @Override
    void populateGrid() {
        double size = instance.getWindowSize();
        opponentsGrid.setMinSize(size - 30, size - 30);
        mineText.setOpacity(0);
        super.populateGrid();
        addDestroyedShipStyles();
    }

    void moveToCenter() {
        super.moveToCenter();

    }

    @FXML
    void handleGridClick(MouseEvent e) {
        try {
            Node source = (Node)e.getTarget() ;
            if (source.getStyleClass().indexOf("empty-cell") == -1) {
                return;
            }
            int colIndex = GridPane.getColumnIndex(source);
            int rowIndex = GridPane.getRowIndex(source);
            final int result = game.makeTurn(colIndex - 1, rowIndex - 1);
            String textToWrite, classToAdd = "";
            switch (result) {
                case 1:
                    textToWrite = "Hit! still your turn";
                    source.getStyleClass().clear();
                    classToAdd = "hit-opponent";
                    break;
                case 0:
                    textToWrite = "Miss.. switching players";
                    source.getStyleClass().clear();
                    classToAdd = "miss";
                    break;
                case -1:
                    return;
                case -2:
                    textToWrite = "Mine hit!! your cell took damage.. switching players";
                    source.getStyleClass().clear();
                    classToAdd = "mine-hit";
                    break;
                default:
                    if (game.isGameFinished()) {
                        textToWrite = "You won!!";
                        instance.setWinningPlayerName(game.getPlayerName());
                        instance.getGameController().gameEnded();
                    } else {
                        textToWrite = "Great hit! ship is destroyed, still your turn";
                        source.getStyleClass().clear();
                        addDestroyedShipStyles();
                    }
                    break;
            }
            source.getStyleClass().add(classToAdd);
            displayMessageOverGrid(textToWrite, result == 1 ? 500 : 1500, result < 1, false);
            updateMenu();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    void addDestroyedShipStyles() {
        game.getDestroyedShips().forEach(this::addShipStyles);
    }
}
