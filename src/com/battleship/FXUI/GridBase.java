package com.battleship.FXUI;

import com.battleship.Logic.Board;
import com.battleship.Logic.Game;
import com.battleship.Logic.Point;
import com.battleship.Logic.Ship;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

abstract class GridBase {
    Game game;

    int gridNum;

    String hitStyle;

    @FXML
    GridPane grid;

    @FXML
    Text playerName;

    void getGameInstance() {
        game = Context.getInstance().getBattleShipGame();
    }

    String getStyleForCell(int i, int j) {
        Board board = game.getMyBoards(game.getNumOfPlayer())[gridNum];
        int[][] boardMatrix = board.getMatrix();
        int cellType = boardMatrix[i - 1][j - 1];
        return getStyleClass(cellType);
    }
    String getStyleClass(int cellType) {
        switch (cellType) {
            case Board.EMPTY_CELL:
                return "empty-cell";
            case Board.MINE:
                return "mine";
            case  Board.MISS:
                return "miss";
            case Board.HIT:
                System.out.println(hitStyle);
                return hitStyle;
            default:
                return "";
        }
    }

    void initialize() {
        Context.getInstance().getGameStartedHandler().selectedProperty().addListener((observable, oldValue, gameStarted) -> {
            if (gameStarted) {
                getGameInstance();
                moveToCenter();
            }
        });
        this.hitStyle = "hit";
    }

    void moveToCenter() {
        int boardSize = game.getBoardSize();
        double windowSize = Context.getInstance().getWindowSize() / 2;
        int loc = (32 * boardSize + 50) / 2;
        grid.setLayoutX(windowSize - loc);
        grid.setLayoutY(windowSize - loc);
    }

    void populateGrid() {
        playerName.setText(game.getPlayerName() + "'s turn");
        grid.getChildren().clear();
        Board thisBoard = game.getMyBoards(game.getNumOfPlayer())[gridNum];
        ColumnConstraints column = new ColumnConstraints(32);
        RowConstraints row = new RowConstraints(32);
        for(int i = 0; i < thisBoard.getSize() + 1; ++i) {
            grid.getColumnConstraints().add(column);
            grid.getRowConstraints().add(row);
        }
        for(int i = 0; i < thisBoard.getSize(); ++i) {
            Text rowNum = new Text(String.valueOf(i + 1));
            Text colNum = new Text(Character.toString((char)('A' + i)));
            rowNum.setWrappingWidth(13);
            rowNum.setTextAlignment(TextAlignment.CENTER);
            colNum.setTextAlignment(TextAlignment.CENTER);
            colNum.setWrappingWidth(32);
            grid.add(rowNum, 0, i + 1);
            grid.add(colNum, i + 1, 0);
        }
        for(int i = 1; i< thisBoard.getSize() + 1; ++i) {
            for(int j = 1; j < thisBoard.getSize() + 1; ++j) {
                try {
                    Pane item = new Pane();
                    item.setMinSize(33,33);
                    item.getStyleClass().add(getStyleForCell(i,j));
                    grid.add(item,i,j);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        moveToCenter();
    }
    void addShipClassToNode(Node n, String style) {
        if (n.getStyleClass().indexOf("hit") == -1) {
            n.getStyleClass().clear();
        }
        n.getStyleClass().add(style);
    }

    private String shipClassToAdd(int i, Point[] points) {
        int x,lastX,nextX,y,lastY,nextY;
        x = points[i].getX();
        y = points[i].getY();
        if (i == 0) {
            if (points.length == 1) {
                return "one-cell";
            } else {
                nextY = points[i + 1].getY();
                return "ship-missing-" + ( nextY > y ? "bottom" : "right");
            }
        }
        lastX = points[i - 1].getX();
        lastY = points[i - 1].getY();
        if (i == points.length - 1) {
            return "ship-missing-" + ( lastY < y ? "top" : "left");
        }
        nextX = points[i + 1].getX();
        nextY = points[i + 1].getY();
        if (nextX != lastX && nextY != lastY) {
            if (lastX > nextX) {
                return "some-class";
            } else {
                return "some-class";
            }
        }
        return "ship-missing-" + (nextY > y ? "top-bottom" : "left-right");
    }


    protected void addShipStyles(Ship ship) {
        Point[] location = ship.getLocation();
        String classToAdd;
        for(int i = 0; i < location.length; ++i) {
            int y = location[i].getY();
            int x = location[i].getX();
            Node cell = gridCell(x + 1,y + 1);
            classToAdd = shipClassToAdd(i, location);
            addShipClassToNode(cell, classToAdd);
            if (ship.getCount() == 0) {
                cell.getStyleClass().add("destroyed-ship");
            }
        }
    }


    Node gridCell(int col, int row) {
        for (Node node : grid.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
}
