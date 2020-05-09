package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class TetrisSettingsController {
    @FXML
    public Button swedishButton;
    @FXML
    public Button englishButton;
    @FXML
    public Button easyButton;
    @FXML
    public Button normalButton;
    @FXML
    public Button hardButton;

    Stage stage;

    TetrisModel tetrisModel = new TetrisModel();

    public void languageButton(ActionEvent actionEvent) {

        if (actionEvent.getSource().equals(englishButton)){
            englishButton.setStyle("-fx-background-color: grey;-fx-font-size:20 ");
            swedishButton.setStyle("-fx-font-size:20 ");
            tetrisModel.setLanguageString("english");

        }
        if (actionEvent.getSource().equals(swedishButton)){
            swedishButton.setStyle("-fx-background-color: grey;-fx-font-size:20 ");
            englishButton.setStyle("-fx-font-size:20 ");
            tetrisModel.setLanguageString("swedish");

        }
    }

    public void difficultyButton(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(easyButton)){
            tetrisModel.setDifficultyString("easy");
            easyButton.setStyle("-fx-background-color: grey;-fx-font-size:20 ");
            hardButton.setStyle("-fx-font-size:20 ");
            normalButton.setStyle("-fx-font-size:20 ");

        }
        if (actionEvent.getSource().equals(normalButton)){
            tetrisModel.setDifficultyString("normal");
            normalButton.setStyle("-fx-background-color: grey;-fx-font-size:20 ");
            easyButton.setStyle("-fx-font-size:20 ");
            hardButton.setStyle("-fx-font-size:20 ");

        }
        if (actionEvent.getSource().equals(hardButton)){
            tetrisModel.setDifficultyString("hard");
            hardButton.setStyle("-fx-background-color: grey; -fx-font-size:20 ");
            easyButton.setStyle("-fx-font-size:20 ");
            normalButton.setStyle("-fx-font-size:20 ");

        }
        System.out.println(tetrisModel.getDifficultyInt());
    }

    public void goBackButton(ActionEvent actionEvent) throws IOException {
        stage = (Stage) easyButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);


        stage.setScene(scene);

    }
}
