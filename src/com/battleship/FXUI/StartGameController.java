package com.battleship.FXUI;

import com.battleship.Logic.Game;
import javafx.beans.value.ObservableBooleanValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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

    @FXML
    private void handleChooseFileClick(ActionEvent event) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Open your game XML file");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
                "xml files (*.xml)", "xml");
        chooser.setFileFilter(xmlfilter);
        int success = chooser.showOpenDialog(null);
        if (success == JFileChooser.APPROVE_OPTION) {
            File selectedXml = chooser.getSelectedFile();
            chosenFileText.setText(selectedXml.getName());
            try {
                Context.getInstance().setBattleShipGame(new Game(selectedXml.getAbsolutePath()));
                gameEntered.setSelected(true);
            } catch (Exception e) {
                errorMessages.setText(e.getMessage());
            }
        }

    }
    @FXML
    private void handleStartClick() {
        Context.getInstance().getGameStartedHandler().setSelected(true);
        startGamePane.setVisible(false);
    }

}
