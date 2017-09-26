package com.battleship.FXUI;

import com.battleship.Logic.Game;
import com.battleship.Logic.Ship;
import com.battleship.Logic.Utils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class GameStatsController {
    @FXML Text playerOne;
    @FXML Text playerTwo;
    @FXML Text scoreOne;
    @FXML Text scoreTwo;
    @FXML Text missesOne;
    @FXML Text missesTwo;
    @FXML Text timeOne;
    @FXML Text timeTwo;
    @FXML Text turns;
    @FXML Text timePassed;
    @FXML Pane gameStats;
    @FXML
    TableView shipTable;
    Game game;
    final ObservableList<ShipsLeft> data = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        Context.getInstance().getGameStartedHandler().selectedProperty().addListener((observable, oldValue, gameStarted) -> {
            if (gameStarted) {
                game = Context.getInstance().getBattleShipGame();
                getStats();
                fillShipTable();
            }
        });
        gameStats.opacityProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue.equals(0.0)) {
                getStats();
                getData();
                shipTable.refresh();
            }
        });
    }
    private void getStats() {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        long [] time =  game.GetTimePass();
        turns.setText(game.getNumOfTurns() + "");
        timePassed.setText(time[0] + ":" + time[1]);
        playerOne.setText(game.getPlayerName(0));
        playerTwo.setText(game.getPlayerName(1));
        scoreOne.setText(String.valueOf(game.getScore(0)));
        scoreTwo.setText(String.valueOf(game.getScore(1)));
        missesOne.setText(String.valueOf(game.getNumberOfMisses(0)));
        missesTwo.setText(String.valueOf(game.getNumberOfMisses(1)));
        timeOne.setText(df.format(game.getAvgTime(0)));
        timeTwo.setText(df.format(game.getAvgTime(1)));
    }

    void fillShipTable() {
        getData();
        TableColumn shipTypes = new TableColumn("Ship Types");
        shipTypes.setCellValueFactory(new PropertyValueFactory<>("shipName"));
        TableColumn first = new TableColumn(playerOne.getText());
        first.setCellValueFactory(new PropertyValueFactory<>("leftInPlayerOne"));
        TableColumn second = new TableColumn(playerTwo.getText());
        second.setCellValueFactory(new PropertyValueFactory<>("leftInPlayerTwo"));
        shipTable.setItems(data);
        shipTable.getColumns().addAll(shipTypes, first, second);

    }

    void getData() {
        data.removeAll(data);
        Utils.typeMap.forEach((s, shipType) -> {
            int leftInOne = 0;
            int leftInTwo = 0;
            for (Ship ship : game.getPlayerShips(0)) {
                if (ship.getType().equals(s) && ship.getCount() > 0) {
                    leftInOne++;
                }
            }
            for (Ship ship : game.getPlayerShips(1)) {
                if (ship.getType().equals(s) && ship.getCount() > 0) {
                    leftInTwo++;
                }
            }
            data.add(new ShipsLeft(s, leftInOne, leftInTwo));
        });
    }
    public static class ShipsLeft {

        private final SimpleStringProperty shipName;
        private final SimpleIntegerProperty leftInPlayerOne;
        private final SimpleIntegerProperty leftInPlayerTwo;

        private ShipsLeft(String shipName, Integer leftOne, Integer leftTwo) {
            this.shipName = new SimpleStringProperty(shipName);
            this.leftInPlayerOne = new SimpleIntegerProperty(leftOne);
            this.leftInPlayerTwo = new SimpleIntegerProperty(leftTwo);
        }
        public String getShipName() {
            return shipName.get();
        }

        public SimpleStringProperty shipNameProperty() {
            return shipName;
        }

        public void setShipName(String shipName) {
            this.shipName.set(shipName);
        }

        public int getLeftInPlayerOne() {
            return leftInPlayerOne.get();
        }

        public SimpleIntegerProperty leftInPlayerOneProperty() {
            return leftInPlayerOne;
        }

        public void setLeftInPlayerOne(int leftInPlayerOne) {
            this.leftInPlayerOne.set(leftInPlayerOne);
        }

        public int getLeftInPlayerTwo() {
            return leftInPlayerTwo.get();
        }

        public SimpleIntegerProperty leftInPlayerTwoProperty() {
            return leftInPlayerTwo;
        }

        public void setLeftInPlayerTwo(int leftInPlayerTwo) {
            this.leftInPlayerTwo.set(leftInPlayerTwo);
        }

    }

}

