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
        addShipStyles();
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

    private void addShipStyles() {
        Ship[] ships = game.getPlayerShips();
        int x, y, lastIndex;
        for (Ship ship : ships) {
            Point[] location = ship.getLocation();
            lastIndex = location.length - 1;
            y = location[0].getY();
            x = location[0].getX();
            Node cell = gridCell(x + 1,y + 1);
            if (location.length == 1) {
                cell.getStyleClass().add("empty-cell");
            } else {
                if (location[1].getY() > y) {
                    cell.getStyleClass().add("ship-missing-bottom");
                } else {
                    cell.getStyleClass().add("ship-missing-right");
                }
            }
            for(int i = 1; i < lastIndex; ++i) {
                y = location[i].getY();
                x = location[i].getX();
                cell = gridCell(x + 1,y + 1);
                if (location[i + 1].getY() > y) {
                    cell.getStyleClass().add("ship-missing-top-bottom");
                } else {
                    cell.getStyleClass().add("ship-missing-left-right");
                }
            }
            y = location[lastIndex].getY();
            x = location[lastIndex].getX();
            cell = gridCell(x + 1,y + 1);
            if (location.length > 1) {
                if (location[lastIndex - 1].getY() < location[lastIndex].getY()) {
                    cell.getStyleClass().add("ship-missing-top");
                } else {
                    cell.getStyleClass().add("ship-missing-left");
                }
            }
        }
    }

}
