package com.battleship.FXUI;

import com.battleship.Logic.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;


public class StartGameController {

    private CheckBox gameEntered = new CheckBox();

    @FXML
    private Pane startGamePane;

    @FXML
    private Button startGameButton;

    @FXML
    private Button chooseFileButton;

    @FXML
    private Text chosenFileText;

    @FXML
    private Text errorMessages;

    @FXML
    private TextField playerOneField;

    @FXML
    private TextField playerTwoField;

    @FXML
    protected void initialize() {
        gameEntered.setSelected(false);
        startGameButton.disableProperty().bind(
            playerTwoField.textProperty().isEmpty()
            .or(playerOneField.textProperty().isEmpty())
            .or(gameEntered.selectedProperty().not())
        );
    }

    private final Context inst = Context.getInstance();
    @FXML
    private void handleChooseFileClick(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open your game XML file");

        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Xml files (*.xml)", "*.xml"));
        try {
            File selectedXml = chooser.showOpenDialog(null);
            chosenFileText.setText(selectedXml.getName());
            Context.getInstance().setCurrentXmlPath(selectedXml.getAbsolutePath());
            inst.setBattleShipGame(new Game(selectedXml.getAbsolutePath()));
            gameEntered.setSelected(true);
        } catch (Exception e) {
            errorMessages.setText(e.getMessage());
        }
    }
    @FXML
    private void handleStartClick() {
        String[] names = new String[2];
        names[0] = playerOneField.getText();
        names[1] = playerTwoField.getText();
        inst.getBattleShipGame().setPlayerNames(names);
        inst.getGameStartedHandler().setSelected(true);
        startGamePane.setVisible(false);
    }

}
