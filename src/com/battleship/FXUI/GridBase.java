package com.battleship.FXUI;

import com.battleship.Logic.Board;
import com.battleship.Logic.Game;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

class GridBase {
    Game game;

    int gridNum;

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
        switch (cellType) {
            case Board.EMPTY_CELL:
                return "empty-cell";
            case Board.MINE:
                return "mine";
            case  Board.MISS:
                return "miss";
            case Board.HIT:
                return "hit";
            default:
                return "";
        }
    }

    void initialize() {
        Context.getInstance().getGameStartedHandler().selectedProperty().addListener((observable, oldValue, gameStarted) -> {
            if (gameStarted) {
                getGameInstance();
            }
        });
    }


    void populateGrid() {
        playerName.setText(String.valueOf(game.getNumOfPlayer()));
        grid.getChildren().clear();
        Board thisBoard = game.getMyBoards(game.getNumOfPlayer())[1];
        int[][] boardMatrix = thisBoard.getMatrix();
        ColumnConstraints column = new ColumnConstraints(32);
        RowConstraints row = new RowConstraints(32);
        for(int i = 0; i < thisBoard.getSize() + 1; ++i) {
            grid.getColumnConstraints().add(column);
            grid.getRowConstraints().add(row);
        }
        for(int i = 0; i < thisBoard.getSize(); ++i) {
            Label rowNum = new Label(String.valueOf(i + 1));
            Label colNum = new Label(Character.toString((char)('A' + i)));
            colNum.setTextAlignment(TextAlignment.CENTER);
            rowNum.setMinWidth(13);
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
