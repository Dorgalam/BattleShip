package com.battleship.FXUI;

import com.battleship.Logic.Board;
import com.battleship.Logic.Game;
import com.battleship.Logic.Point;
import com.battleship.Logic.Ship;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ArrayList;

abstract class GridBase {
    Game game;
    int gridNum;
    private boolean rewindMode = false;
    private Context instance = Context.getInstance();
    String hitStyle;

    @FXML GridPane grid;

    @FXML Pane rewindButtons;
    @FXML Button nextButton;
    @FXML Button prevButton;
    @FXML Button quitButton;
    @FXML Button restartButton;
    @FXML Text turnOutcome;


    @FXML Hyperlink quitAction;

    @FXML Text playerMessage;
    @FXML Text playerScore;
    @FXML Text playerHits;
    @FXML Text playerMines;
    @FXML Text minesLeft;
    @FXML Text mineText;
    @FXML Text playerName;
    @FXML Pane minePane;

    private void getGameInstance() {
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
        quitAction.setOnMouseClicked(event -> {
            game.updatePlayers();
            game.updateTurnEndStatus(-3);
            Context.getInstance().getGameStartedHandler().setSelected(false);
        });
        nextButton.setOnMouseClicked(event -> {
            turnOutcome.setText(game.nextPlayer());
            populateGrid();
        });
        prevButton.setOnMouseClicked(event -> {
            turnOutcome.setText(game.prevPlayer());
            populateGrid();
        });
        quitButton.setOnMouseClicked(event -> System.exit(0));
        restartButton.setOnMouseClicked(event -> {

        });
        grid.getParent().opacityProperty().addListener((observable, oldValue, newValue) -> {
            if(Context.getInstance().isRewindMode()) {
                turnOutcome.setText(Context.getInstance().getTurnOutcome());
            }
        });
        this.hitStyle = "hit";
    }


    void moveToCenter() {
        int boardSize = game.getBoardSize();
        double windowSize = Context.getInstance().getWindowSize() / 2;
        int loc = (32 * boardSize + 50) / 2;
        grid.setLayoutX(windowSize - loc);
        grid.setLayoutY(windowSize - loc + (rewindMode ? 30 : 0));
        playerMessage.setLayoutY(windowSize / 2 + 9);
        playerMessage.setTextAlignment(TextAlignment.CENTER);
        playerMessage.setLayoutX(0);
        playerMessage.setWrappingWidth(windowSize * 2);

    }

    void updateMenu() {
        playerScore.setText(game.getScore() + "");
        playerHits.setText(game.getNumberOfHits() + "");
        if (game.getGameMode() == Game.ADVANCED) {
            minePane.setDisable(false);
            minePane.setOpacity(1);
            int numMines = game.getNumMines();
            minesLeft.setText(numMines + "");
            if (numMines == 0) {
                mineText.setOpacity(0);
            }
        }
    }

    void changeAccordingToRewind() {
        grid.setDisable(true);
        rewindButtons.setDisable(false);
        nextButton.setPickOnBounds(true);
        rewindButtons.setOpacity(1);
        minePane.setDisable(true);
        quitAction.setDisable(true);
        int playerIterator = game.getPlayerIterator();
        int turnsListLength = game.getTurnsListLength();
        nextButton.setDisable(playerIterator >= turnsListLength);
        prevButton.setDisable(playerIterator == 1);
    }

    void populateGrid() {
        playerName.setText(game.getPlayerName());
        grid.getChildren().clear();
        updateMenu();
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
                    Pane item = createCellItem();
                    item.getStyleClass().add(getStyleForCell(i,j));
                    grid.add(item,i,j);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        if (Context.getInstance().isRewindMode()) {
            changeAccordingToRewind();
        }
        moveToCenter();
    }

    Pane createCellItem() {
        Pane item = new Pane();
        item.setMinSize(33,33);
        return item;
    }

    void addShipClassToNode(Node n, String style) {
        if (n.getStyleClass().indexOf("hit") == -1) {
            n.getStyleClass().clear();
        }
        n.getStyleClass().add(style);
    }

    void displayMessageOverGrid(String textToWrite, int time, boolean shouldPopulate, boolean moveToFirstTab) {
        grid.setDisable(true);
        grid.setOpacity(0.2);
        playerMessage.setText(textToWrite);
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(time),
                ae -> {
                    playerMessage.setText("");
                    if (moveToFirstTab) {
                        instance.getGameTabs().getSelectionModel().selectFirst();
                    }
                    if (shouldPopulate) {
                        populateGrid();
                    }
                    TransitionEffects.fadeEffect(grid, 0.2, 500).setOnFinished(finished -> {
                        grid.setDisable(false);
                    });
                }));
        timeline.play();
    }

    private String shipClassToAdd(int i, int length, int dir) {
        switch (dir) {
            case Board.ROW:
                if (i == 0) {
                    return "ship-missing-right";
                } else if (i == length - 1) {
                    return "ship-missing-left";
                } else {
                    return "ship-missing-left-right";
                }
            case Board.COL:
                if (i == 0) {
                    return "ship-missing-bottom";
                } else if (i == length - 1) {
                    return "ship-missing-top";
                } else {
                    return "ship-missing-top-bottom";
                }
            case Board.UP_RIGHT:
                if (i == 0) {
                    return "ship-missing-top";
                } else if (i == length / 2) {
                    return "ship-missing-bottom-right";
                } else if (i == length - 1) {
                    return "ship-missing-left";
                } else if (i < length / 2) {
                    return "ship-missing-top-bottom";
                } else {
                    return "ship-missing-left-right";
                }
            case Board.RIGHT_DOWN:
                if (i == 0) {
                    return "ship-missing-right";
                } else if (i == length / 2) {
                    return "ship-missing-bottom-left";
                } else if (i == length - 1) {
                    return "ship-missing-top";
                } else if (i < length / 2) {
                    return "ship-missing-left-right";
                } else {
                    return "ship-missing-top-bottom";
                }
            case Board.DOWN_RIGHT:
                if (i == 0) {
                    return "ship-missing-bottom";
                } else if (i == length / 2) {
                    return "ship-missing-top-right";
                } else if (i == length - 1) {
                    return "ship-missing-left";
                } else if (i < length / 2) {
                    return "ship-missing-top-bottom";
                } else {
                    return "ship-missing-left-right";
                }
            case Board.RIGHT_UP:
                if (i == 0) {
                    return "ship-missing-right";
                } else if (i == length / 2) {
                    return "ship-missing-top-left";
                } else if (i == length - 1) {
                    return "ship-missing-bottom";
                } else if (i < length / 2) {
                    return "ship-missing-left-right";
                } else {
                    return "ship-missing-top-bottom";
                }
            default:
                return "ship";
        }
    }


    protected void addShipStyles(Ship ship) {
        Point[] location = ship.getLocation();
        String classToAdd;
        for(int i = 0; i < location.length; ++i) {
            int y = location[i].getY();
            int x = location[i].getX();
            Node cell = gridCell(x + 1,y + 1);
            classToAdd = shipClassToAdd(i, location.length, ship.getDir());
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
