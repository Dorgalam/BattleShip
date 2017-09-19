package com.battleship.FXUI;

import com.battleship.Logic.Board;
import com.battleship.Logic.Game;
import com.battleship.Logic.Point;
import com.battleship.Logic.Ship;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import jdk.nashorn.internal.objects.annotations.Function;

import java.io.File;
import java.util.List;


public class YourGridController extends GridBase {

    @FXML
    public Pane yourGrid;

    @FXML private GridPane grid;

    @FXML
    private ImageView mineImage;

    @FXML
    Pane minePane;

    @FXML
    Text minesLeft;

    @FXML
    Text mineText;

    @FXML
    void populateGrid() {
        double size = Context.getInstance().getWindowSize();
        yourGrid.setPrefSize(size, size);
        super.populateGrid();
        addShipsStyles();
        updateMenu();
    }


    @Override
    Pane createCellItem() {
        Pane item = super.createCellItem();
        item.setOnDragEntered(event -> {
            int x = (int)item.getLayoutX() / 32;
            int y = (int)item.getLayoutY() /32;
            if (game.isValidPlaceForMine(x, y)) {
                item.getStyleClass().add("mine-hover");
            } else {
                item.getStyleClass().add("mine-cant-place");
            }
        });
        item.setOnDragExited(event -> {
            item.getStyleClass().remove("mine-hover");
            item.getStyleClass().remove("mine-cant-place");
        });
        return item;
    }

    @FXML
    protected void initialize() {
        super.initialize();
        this.gridNum = 0;
        yourGrid.opacityProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.equals(0.0)) {
                populateGrid();
            }
        });
        grid.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.MOVE);
        });
        grid.setOnDragDropped(event -> {
            Node target = (Node)event.getTarget();
            int x = (int)target.getLayoutX();
            int y = (int)target.getLayoutY();
            if (x < 13 || y < 16) {
                return;
            }
            x /= 32;
            y /= 32;
            int time = 0;
            String textToWrite = "";
            if (game.isValidPlaceForMine(x, y)) {
                switch (game.putMine(x, y)) {
                    case 1:
                        target.getStyleClass().add("mine");
                        textToWrite = "Mine placed! switching players..";
                        time = 1500;
                        break;
                    case 2:
                        textToWrite = "Wrong mine placement, can't be near a ship";
                        time = 500;
                        break;
                    case 0:
                        System.out.println("no mines left (won't be possible)");
                        break;
                }
                updateMenu();
                displayMessageOverGrid(textToWrite, time, time == 1500,time == 1500);
            }
            event.consume();
        });
        mineImage.setOnDragDetected(event -> {
            Dragboard db = mineImage.startDragAndDrop(TransferMode.MOVE);
            db.setDragView(mineImage.getImage());
            ClipboardContent content = new ClipboardContent();
            content.putString("mine");
            db.setContent(content);
            ((Node) event.getSource()).setCursor(Cursor.HAND);
            event.consume();
        });
    }

    @Override
    String getStyleForCell(int i, int j) {
        int x = i - 1;
        int y = j - 1;
        Board opponentsBoard = game.getMyBoards(1 - game.getNumOfPlayer())[1];
        Board myBoard = game.getMyBoards(game.getNumOfPlayer())[0];
        int cellType = (myBoard.getSquare(x, y) == Board.MINE ? Board.MINE : opponentsBoard.getSquare(x, y));
        return getStyleClass(cellType);
    }
    private void addShipsStyles() {
        for (Ship ship : game.getPlayerShips()) {
            addShipStyles(ship);
        }
    }

}
