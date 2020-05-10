package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.*;

import javafx.scene.input.KeyEvent;


public class gameSceneController {

    @FXML
    public Label pausedText;
    @FXML
    public Button pauseButton;
    @FXML
    public GridPane nextPieceGrid;
    public Button resumeButton;
    public Label scoreLabel;
    public Label nextLabel;
    public Label gameInformation;
    public Label levelLabel;
    public VBox pauseMenu;
    public Label gameOverText;
    public VBox gameOverMenu;
    public Label highScoreText;
    public Label yourScoreText;
    public Label highScoreOne;
    public Label highScoreTwo;
    public Label highScoreThree;


    private ArrayList<Integer> activeCordinates = new ArrayList<>(50);

    TetrisModel tetrisModel = new TetrisModel();
    @FXML
    Label score;

    @FXML
    Button exitButton;

    @FXML
    GridPane tetrisGrid = new GridPane();


    @FXML
    BorderPane borderPane;

    private Stage stage;

    private String activePieceString;

    private int gameSpeedMilliseconds;

    private pieceFactory pieces = new pieceFactory();

    private Boolean gamePaused = false;

    private ArrayList<ArrayList<Rectangle>> gridArray = new ArrayList<>(999);

    private Boolean changeSpeed = false;

    private int mainLoopCount = 0;

    private ArrayList<ArrayList<Rectangle>> gridArrayNext = new ArrayList<>(999);

    private int clearLineCount = 1;

    private Clip music;

    private long musicCurrentTimeInMicroseconds;

    @FXML
    public void initialize() throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        // musik kod tagen från https://www.geeksforgeeks.org/play-audio-file-using-java/
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(gameSceneController.class.getResource("tetrismusic.wav"));
        music = AudioSystem.getClip();
        music.open(audioIn);
        music.start();


        pieces.setRandomPieceArray();

        setGridArray();
        gridCreator();

        gameSpeedMilliseconds = tetrisModel.getDifficultyInt();


        if (tetrisModel.getLanguageString().equals("swedish")) {

            setLanguageSwedish();

        }

        // gameInformation.setText("Language "+tetrisModel.getLanguageString()+": Difficulty "+tetrisModel.getDifficultyString());


        tetrisModel.initiateOccupiedCordinates();

        tetrisModel.setScore(0);

        score.setText(Integer.toString(tetrisModel.getScore()));

        activePieceString = pieces.getRandomPiece().get(0);


        gameLoop();


    }

    /**
     * main loopen som körs när spelet är igång
     *
     *
     */
    private void gameLoop() {

        while (true) {


            Timer fall = new Timer();
            TimerTask task = new TimerTask() {

                int level = 1;

                public void run() {

                    if (gamePaused == false) {


                        Platform.runLater(new Runnable() {

                            public void run() {
                                if (checkIfCordinateIsOccupied() || activeCordinates.contains(19)) {
                                    mainLoopCount = 0;

                                    tetrisModel.addOccupiedCordinates(activeCordinates);
                                    pieces.randomizeNewPiece();
                                    activePieceString = pieces.getRandomPiece().get(0);
                                }
                                if (mainLoopCount == 0) {


                                    initialValueActiveCordinates( pieces.getPieceSize(activePieceString));
                                }

                                addPieceNextGrid();


                                if (mainLoopCount > 0) {

                                    removePieces(activeCordinates);
                                    placePieceOneDown(activeCordinates);
                                }
                                checkIfLineIsFull();
                                mainLoopCount++;


                                // om kordinaten är tagen eller det är den sista raden


                                // var tionde rad som blir borttagen så ökar leveln, svårhetsgraden
                                if (clearLineCount % 11 == 0 && clearLineCount != 0) {
                                    level++;
                                    levelLabel.setText("Level " + level);

                                    if (gameSpeedMilliseconds > 10)
                                        gameSpeedMilliseconds = gameSpeedMilliseconds - 10;
                                    clearLineCount++;

                                    changeSpeed = true;

                                }



                                // när spelaren förlorar
                                if (tetrisModel.getOccupiedCordinates().get(0).size() > 0) {
                                    // stop music
                                    music.stop();

                                    // sparar highscore
                                    try {
                                        tetrisModel.saveHighscore(Integer.toString(tetrisModel.getScore()));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    yourScoreText.setText("YOUR SCORE: " + Integer.toString(tetrisModel.getScore()));

                                    highScoreOne.setText("1: " + tetrisModel.getHighscore().get(0));

                                    highScoreTwo.setText("2: " + tetrisModel.getHighscore().get(1));

                                    highScoreThree.setText("3: " + tetrisModel.getHighscore().get(2));

                                    gameOverMenu.setVisible(true);

                                    fall.cancel();


                                }


                                if (changeSpeed == true) {
                                    fall.cancel();
                                    changeSpeed = false;
                                    gameLoop();

                                }


                            }
                        });
                    }
                }
            };
            fall.schedule(task, 0, gameSpeedMilliseconds);


            break;
        }

    }

    /**
     * fixar det grafiska för next klossarna
     */
    private void addPieceNextGrid() {
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 4; j++) {
                gridArrayNext.get(i).get(j).setFill(Color.DARKBLUE);
                gridArrayNext.get(i).get(j).setStroke(Color.DARKBLUE);
            }

        }

        // ger de kordinaterna där som nästa piece har och ger den sin fill och stroke
        for (int i = 0; i < 8; i++) {
            gridArrayNext.get(pieces.getPieceSize(pieces.getRandomPiece().get(1))[i + 1]).get(pieces.getPieceSize(pieces.getRandomPiece().get(1))[i] - 4).setFill(pieces.getPieceColor(pieces.getRandomPiece().get(1)));
            gridArrayNext.get(pieces.getPieceSize(pieces.getRandomPiece().get(1))[i + 1]).get(pieces.getPieceSize(pieces.getRandomPiece().get(1))[i] - 4).setStroke(Color.GRAY);
            i++;

        }
        for (int i = 0; i < 8; i++) {
            gridArrayNext.get(pieces.getPieceSize(pieces.getRandomPiece().get(2))[i + 1] + 5).get(pieces.getPieceSize(pieces.getRandomPiece().get(2))[i] - 4).setFill(pieces.getPieceColor(pieces.getRandomPiece().get(2)));
            gridArrayNext.get(pieces.getPieceSize(pieces.getRandomPiece().get(2))[i + 1] + 5).get(pieces.getPieceSize(pieces.getRandomPiece().get(2))[i] - 4).setStroke(Color.GRAY);
            i++;

        }
        for (int i = 0; i < 8; i++) {
            gridArrayNext.get(pieces.getPieceSize(pieces.getRandomPiece().get(3))[i + 1] + 10).get(pieces.getPieceSize(pieces.getRandomPiece().get(3))[i] - 4).setFill(pieces.getPieceColor(pieces.getRandomPiece().get(3)));
            gridArrayNext.get(pieces.getPieceSize(pieces.getRandomPiece().get(3))[i + 1] + 10).get(pieces.getPieceSize(pieces.getRandomPiece().get(3))[i] - 4).setStroke(Color.GRAY);
            i++;

        }


    }

    /**
     * sätter alla texter till svenska
     */
    private void setLanguageSwedish() {
        scoreLabel.setText("Poäng");
        nextLabel.setText("Nästa");
        pausedText.setText("Pausad");
        resumeButton.setText("Återuppta");
        pauseButton.setText("Lämna spel");
        gameOverText.setText("SPELET ÖVER");
        highScoreText.setText("HÖGSTA POÄNG");
        yourScoreText.setText("DINA POÄNG:");
    }

    /**
     * om en rad är full så tas en rad bort och spelaren får poäng
     */
    private void checkIfLineIsFull() {

        for (int i = 0; i < 21; i++) {

            if (tetrisModel.getOccupiedCordinates().get(i).size() == tetrisModel.getGridWidth()) {

                removeLine(i);
                clearLineCount++;
                tetrisModel.setScore(tetrisModel.getScore() + 40);
                score.setText(Integer.toString(tetrisModel.getScore()));

            }
        }


    }

    /**
     * Tar bort en rad och flyttar alla rektanglar ett steg ner
     * @param row
     */
    private void removeLine(int row) {

        for (int i = 0; i < tetrisModel.getGridWidth(); i++) {

            removePiece(i, row);

        }

        // get score
        tetrisModel.removeOccupiedCordinatesRow(row);


        moveAllPiecesDown(row);
        //remove active pieces


    }

    /**
     * Tar in en rad och flyttar alla occupiedpieces över raden ett steg neråt
     *
     * @param row
     */
    private void moveAllPiecesDown(int row) {


        // loopar igenom griden från den raden tas bort och uppåt


        for (int i = row - 1; i != 0; i--) {
            for (int j = 0; j < tetrisModel.getGridWidth(); j++) {

                if (tetrisModel.getOccupiedCordinates().get(i).contains(j)) {

                    gridArray.get(i + 1).get(j).setFill(gridArray.get(i).get(j).getFill());
                    removePiece(j, i);

                    tetrisModel.removeOccupiedCordinates(j, i);
                    tetrisModel.addSingleCordinateOccupiedCordinates(j, i + 1);
                }
            }
        }


    }

    /**
     * kollar om de aktiva kordinaterna redan är använda
     *
     * @return
     */
    private Boolean checkIfCordinateIsOccupied() {
        for (int i = 0; i < activeCordinates.size(); i++) {
            // om activeCordinates tillhör occupiedCordinates så blir det true

            if (tetrisModel.getOccupiedCordinates().get(activeCordinates.get(i + 1) + 1).contains(activeCordinates.get(i))) {




                return true;
            }

            i++;
        }

        return false;
    }

    private void setGridArray() {
        for (int i = 0; i < 25; i++) {
            gridArray.add(new ArrayList<>());
            gridArrayNext.add(new ArrayList<>());
        }

    }


    /**
     * Skapar ett 10*20 grid med hjälp av en arraylist med arraylist av rektanglar
     */
    private void gridCreator() {



        int x = 0;
        int testt = 0;

        for (int i = 0; i < tetrisModel.getGridWidth() * tetrisModel.getGridHeight(); i++) {
            Rectangle temp = new Rectangle(40, 40);
            temp.setFill(Color.BLACK);
            temp.setStroke(Color.GREY);
            if (i % tetrisModel.getGridWidth() == 0 && i > 0) {
                x++;
                testt = 0;
            }
            gridArray.get(x).add(temp);


            tetrisGrid.addRow(x, gridArray.get(x).get(testt));

            testt++;
        }

        x = 0;
        testt = 0;
        for (int i = 0; i < 4 * 14; i++) {

            if (i % 4 == 0 && i != 0) {
                x++;
                testt = 0;
            }
            Rectangle temp1 = new Rectangle(40, 40);


            gridArrayNext.get(x).add(temp1);
            nextPieceGrid.addRow(x, gridArrayNext.get(x).get(testt));
            testt++;
        }


    }

    /**
     * ger en av rektanglarna i kordinatsystemet en färg
     * @param x x kordinat
     * @param y y kordinat
     */
    private void addPiece(int x, int y) {

        gridArray.get(y).get(x).setFill(pieces.getPieceColor(activePieceString));


    }

    /**
     * ger en av rektanglarna i kordinatsystemet svart färg
     * @param x x kordinat
     * @param y y kordinat
     */
    private void removePiece(int x, int y) {

        gridArray.get(y).get(x).setFill(Color.BLACK);

    }


    /**
     * Förflyttar den aktiva klossen ett steg till höger om det är möjligt
     */
    private void moveActivePieceRight() {
        // kolla om eccupied piece är brevid
        Boolean possibleMove = true;


        for (int i = 0; i < activeCordinates.size(); i++) {

            // om kordinaten höger om den aktiva kordinaten är upptagen
            if (tetrisModel.getOccupiedCordinates().get(activeCordinates.get(i + 1)).contains(activeCordinates.get(i) + 1)) {
                possibleMove = false;
                System.out.println("NOT POSSIBLE MOVE");

            }
            i++;

        }


        for (int i = 0; i < activeCordinates.size(); i++) {


            if (activeCordinates.get(i) == 9) {
                possibleMove = false;
            }
            i++;

        }
        if (possibleMove == true) {

            removePieces(activeCordinates);

            for (int i = 0; i < activeCordinates.size(); i++) {
                activeCordinates.set(i, activeCordinates.get(i) + 1);
                i++;
            }


            placePiece(activeCordinates);

        }


    }

    /**
     * Förflyttar den aktiva klossen ett steg till vänster om det är möjligt
     */
    private void moveActivePieceLeft() {
        Boolean possibleMove = true;

        for (int i = 0; i < activeCordinates.size(); i++) {

            // om kordinaten höger om den aktiva kordinaten är upptagen
            if (tetrisModel.getOccupiedCordinates().get(activeCordinates.get(i + 1)).contains(activeCordinates.get(i) - 1)) {
                possibleMove = false;
                System.out.println("NOT POSSIBLE MOVE");

            }
            i++;

        }

        for (int i = 0; i < activeCordinates.size(); i++) {


            if (activeCordinates.get(i) == 0) {
                possibleMove = false;
            }
            i++;

        }
        if (possibleMove == true) {
            removePieces(activeCordinates);

            for (int i = 0; i < activeCordinates.size(); i++) {
                activeCordinates.set(i, activeCordinates.get(i) - 1);
                i++;
            }


            placePiece(activeCordinates);

        }


    }

    /**
     * ger den nya klossen sina första aktiva kordinater
     * @param cordinates
     */
    private void initialValueActiveCordinates( int[] cordinates) {
        activeCordinates = new ArrayList<>();
        for (int i = 0; i < cordinates.length; i++) {

            addPiece(cordinates[i], cordinates[i + 1]);

            activeCordinates.add(cordinates[i]);
            activeCordinates.add(cordinates[i + 1]);
            i++;

        }


    }

    /**
     * förflyttar en kloss ett steg ner
     * @param cordinates
     */
    private void placePieceOneDown(ArrayList<Integer> cordinates) {
        activeCordinates = new ArrayList<>();
        for (int i = 0; i < cordinates.size(); i++) {


            if (cordinates.get(i + 1) + 1 >= tetrisModel.getGridHeight()) {
            } else {
                addPiece(cordinates.get(i), cordinates.get(i + 1) + 1);
            }

            activeCordinates.add(cordinates.get(i));
            activeCordinates.add(cordinates.get(i + 1) + 1);
            i++;

        }


    }

    /**
     * gör ett antal kordinater till aktiva
     * @param cordinates
     */
    private void placePiece(ArrayList<Integer> cordinates) {
        activeCordinates = new ArrayList<>();
        for (int i = 0; i < cordinates.size(); i++) {


            addPiece(cordinates.get(i), cordinates.get(i + 1));

            activeCordinates.add(cordinates.get(i));
            activeCordinates.add(cordinates.get(i + 1));
            i++;

        }

    }



    public void exitButton(ActionEvent actionEvent) {
    }

    /**
     * Tar hand om pause knappen
     * @param mouseEvent
     * @throws InterruptedException
     */
    public void onMousePressed(MouseEvent mouseEvent) throws InterruptedException {
        System.out.println("PAUSE");

        if (gamePaused == true) {
            music.start();
            gamePaused = false;
            pauseMenu.setVisible(false);


        } else {
            music.stop();

            gamePaused = true;
            pauseMenu.setVisible(true);
        }

    }

    /**
     * när s knappen släpps så blir hastigheten normal igen
     * @param e
     */
    public void handleKeyReleased(KeyEvent e) {
        switch (e.getCode()) {

            case S:
                changeSpeed = true;
                gameSpeedMilliseconds = tetrisModel.getDifficultyInt();

                break;


            default:
                break;
        }


    }

    /**
     * all input från tangentbordet hanteras
     * @param e
     */
    public void handleKeyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case D:
                moveActivePieceRight();
                break;
            case A:
                moveActivePieceLeft();
                break;
            case S:
                gameSpeedMilliseconds = 50;
                changeSpeed = true;

                break;
            case W:
                rotatePiece();
                break;
            case V:
                break;


            default:
                break;
        }

    }

    /**
     * Roterar en kloss om det är möjligt
     * formel tagen från http://tech.migge.io/2017/02/07/tetris-rotations/
     * x new = -y old
     * y new = x old
     */
    private void rotatePiece() {
        try {
            ArrayList<Integer> xCordinates = new ArrayList<>();
            ArrayList<Integer> yCordinates = new ArrayList<>();
            ArrayList<Integer> Cordinates = new ArrayList<>();
            ArrayList<Integer> rotatedActiveCordinates = new ArrayList<>();

            // ger x cordinates och y cordinates sitt kordinat värde relativt till den första punkten i activecordinates
            for (int i = 0; i < activeCordinates.size(); i++) {
                if (i != 0) {
                    xCordinates.add(activeCordinates.get(0) - activeCordinates.get(i));
                    yCordinates.add(activeCordinates.get(1) - activeCordinates.get(i + 1));
                }
                i++;
            }

            int x_new;
            int y_new;
            for (int i = 0; i < 3; i++) {
                x_new = -yCordinates.get(i);
                y_new = xCordinates.get(i);
                Cordinates.add(x_new);
                Cordinates.add(y_new);
            }


            // lägger till den punkten klossen roteras runt
            rotatedActiveCordinates.add(activeCordinates.get(0));
            rotatedActiveCordinates.add(activeCordinates.get(1));
            for (int i = 0; i < 6; i++) {
                rotatedActiveCordinates.add(activeCordinates.get(0) - Cordinates.get(i));
                rotatedActiveCordinates.add(activeCordinates.get(1) - Cordinates.get(i + 1));
                i++;

            }
            Boolean rotatePossible = true;


            for (int j = 0; j < rotatedActiveCordinates.size(); j++) {
                if (tetrisModel.getOccupiedCordinates().get(rotatedActiveCordinates.get(j + 1)).contains(rotatedActiveCordinates.get(j))) {
                    rotatePossible = false;
                    System.out.println("ROTATE NOT POSSIBLE");
                }
                // om de nya kordinaterna är utanför griden i x axeln
                if (rotatedActiveCordinates.get(j) > 9 || rotatedActiveCordinates.get(j) < 0) {
                    rotatePossible = false;
                }
                //om de nya kordinaterna är utanför griden i y axeln
                if (rotatedActiveCordinates.get(j + 1) > 19 || rotatedActiveCordinates.get(j + 1) < 0) {
                    rotatePossible = false;
                }
                j++;

            }


            if (rotatePossible) {

                removePieces(activeCordinates);
                placePiece(rotatedActiveCordinates);


            }
        } catch (Exception e) {

        }


    }

    /**
     * Om exit knappen klickas
     * @param actionEvent
     * @throws IOException
     */
    public void pauseButtonAction(ActionEvent actionEvent) throws IOException {
        music.stop();
        if (gamePaused == true) {
            System.out.println("EXITT");
            stage = (Stage) pauseButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Scene scene = new Scene(root);


            stage.setScene(scene);
        }

    }


    public void resumeGame(ActionEvent actionEvent) throws InterruptedException {
        onMousePressed(null);
    }

    /**
     * Ger kordinaterna som ges färgen svart
     * @param cordinates
     */
    private void removePieces(ArrayList<Integer> cordinates) {

        for (int i = 0; i < cordinates.size(); i++) {
            if (cordinates.get(i + 1) == 0)
                removePiece(cordinates.get(i), cordinates.get(i + 1));
            else {
                removePiece(cordinates.get(i), cordinates.get(i + 1));
            }
            i++;
        }
    }

    /**
     * om play again knappen klickas så börjar spelet om
     * @param actionEvent
     * @throws IOException
     */
    public void playAgainButton(ActionEvent actionEvent) throws IOException {
        stage = (Stage) pauseButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("gameScene.fxml"));
        Scene scene = new Scene(root);


        stage.setScene(scene);


    }

    /**
     * om main menu knappen klickas så öppnas start menyn
     * @param actionEvent
     * @throws IOException
     */
    public void mainMenuButton(ActionEvent actionEvent) throws IOException {

        music.stop();

        stage = (Stage) pauseButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);


        stage.setScene(scene);


    }
}