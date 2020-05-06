package sample;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class TetrisController {
    Stage stage;

    @FXML
    Button exitButton;

    @FXML
    Button startGameButton;
    @FXML
    public void initialize(){

    }
    public void startGameButton(ActionEvent actionEvent) throws IOException {


        System.out.println("TESTTEST");
        stage = (Stage) startGameButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("gameScene.fxml"));
        Scene scene = new Scene(root);


        stage.setScene(scene);


    }

    // Stänger ner fönstret när exit knappen trycks
    public void exitButton(ActionEvent actionEvent) {
        stage = (Stage) exitButton.getScene().getWindow();

        System.out.println("EXIT");
        stage.close();
    }



    public void hehe(ActionEvent actionEvent) {
    }

    public void settingsButton(ActionEvent actionEvent) throws IOException {

        stage = (Stage) startGameButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("TetrisSettings.fxml"));
        Scene scene = new Scene(root);


        stage.setScene(scene);

    }

    public void highScoreButton(ActionEvent actionEvent) {
    }
}
