package com.battleship.FXUI;

import com.battleship.Logic.Board;
import com.battleship.Logic.Game;
import com.battleship.Logic.Point;
import com.battleship.Logic.Ship;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;



public class YourGridController extends GridBase {

    @FXML
    public Pane yourGrid;


    @FXML
    void populateGrid() {
        super.populateGrid();
        addShipsStyles();
    }

    @FXML
    protected void initialize() {
        super.initialize();
        this.gridNum = 0;
        System.out.println(yourGrid.getOpacity());
        yourGrid.opacityProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.equals(0.0)) {
                populateGrid();
            }
        });
    }

    @Override
    String getStyleForCell(int i, int j) {
        Board board = game.getMyBoards(1 - game.getNumOfPlayer())[1];
        int[][] boardMatrix = board.getMatrix();
        int cellType = boardMatrix[i - 1][j - 1];
        return getStyleClass(cellType);
    }
    private void addShipsStyles() {
        for (Ship ship : game.getPlayerShips()) {
            addShipStyles(ship);
        }
    }

}
