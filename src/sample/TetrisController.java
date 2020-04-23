package sample;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TetrisController {
    Stage stage;

    @FXML
    Button exitButton;

    @FXML
    Button startGameButton;

    public void startGameButton(ActionEvent actionEvent) throws IOException {

        System.out.println("TESTTEST");
        stage = (Stage) startGameButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("gameScene.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);

        gameSceneController gameScene= new gameSceneController();

    }

    // Stänger ner fönstret när exit knappen trycks
    public void exitButton(ActionEvent actionEvent) {
        stage = (Stage) exitButton.getScene().getWindow();

        System.out.println("EXIT");
        stage.close();
    }
}
