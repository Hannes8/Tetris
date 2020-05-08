package sample;


import com.sun.org.apache.xpath.internal.operations.Bool;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.*;

import javafx.scene.input.KeyEvent;


public class gameSceneController{

    @FXML
    public Label pausedText;
    @FXML
    public Button pauseButton;


    private ArrayList <Integer> activeCordinates = new ArrayList<>(50);

    TetrisModel tetrisModel = new TetrisModel();
    @FXML
    Label score;

    @FXML
    Button exitButton;

    @FXML
    GridPane tetrisGrid = new GridPane();


    @FXML
    BorderPane borderPane;

    Stage stage;

    String activePieceString;

    private int gameSpeedMilliseconds = 250;

    private pieceFactory pieces = new pieceFactory();

    private Boolean gamePaused = false;

    ArrayList <ArrayList<Rectangle>> gridArray=new ArrayList<>(999);

    Boolean changeSpeed = false;

    int x = 0;



    @FXML
    public void initialize() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        //AudioInputStream audioIn = AudioSystem.getAudioInputStream(gameSceneController.class.getResource("tetrismusic.wav"));
        //Clip clip = AudioSystem.getClip();
        //clip.open(audioIn);
      //  clip.start();

        tetrisModel.saveHighscore();

        setGridArray();
        gridCreator();



            tetrisModel.initiateOccupiedCordinates();

            tetrisModel.setScore(555);

        System.out.println(tetrisModel.getScore());
        score.setText(Integer.toString(tetrisModel.getScore()));

        activePieceString = pieces.getRandomPiece();
        System.out.println(activePieceString+" PIECEEEEEEEEEEEEEEEEEEE");

        gameLoop();


    }
    private void gameLoop(){

        while (true){



            Timer fall = new Timer();
            TimerTask task = new TimerTask() {
                int count = 0;
                String piece;
                public void run() {

                    if (gamePaused == false) {

                        Platform.runLater(new Runnable() {
                            public void run() {
                                if (x == 0) {
                                    initialValueActiveCordinates(pieces.getPieceColor(activePieceString), pieces.getPieceSize(activePieceString), x);
                                }

                                    if (x > 0) {
                                    removePieces(activeCordinates);
                                    placePieceOneDown(activeCordinates);
                                }

                                x++;
                                // om kordinaten är tagen eller det är den sista raden
                                if (checkIfCordinateIsOccupied() || x == 20) {


                                    x = 0;

                                    tetrisModel.addOccupiedCordinates(activeCordinates);
                                    activePieceString = pieces.getRandomPiece();
                                }


                                score.setText(Integer.toString(tetrisModel.getScore() + x));

                                if (tetrisModel.getOccupiedCordinates().get(0).contains(5)) {

                                    System.out.println("GAME OVER");
                                }
                                checkIfLineIsFull();

                                if (changeSpeed==true){
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

    private void checkIfLineIsFull() {
        for (int i = 0; i <21 ; i++) {

                if (tetrisModel.getOccupiedCordinates().get(i).size()==10){

                    removeLine(i);
                }
        }


    }

    private void removeLine(int row) {
        System.out.println("REMOVING LINE");

        for (int i = 0; i < 10; i++) {
            removePiece(i,row-1);

        }

        // get score
        tetrisModel.removeOccupiedCordinatesRow(row);
        //remove active pieces

        moveAllPiecesDown();

    }

    private void moveAllPiecesDown() {
        for (int i = 0; i < 21 ; i++) {
            for (int j = 0; j <tetrisModel.getOccupiedCordinates().get(i).size() ; j++) {
                activeCordinates.add(tetrisModel.getOccupiedCordinates().get(i).get(j));
                activeCordinates.add(i);
            }

        }
        System.out.println(activeCordinates);



    }

    /**
     * kollar om de aktiva kordinaterna redan är använda
     * @return
     */
    private Boolean checkIfCordinateIsOccupied(){
        for (int i = 0; i <activeCordinates.size() ; i++) {
            // om activeCordinates tillhör occupiedCordinates så blir det true

            if (tetrisModel.getOccupiedCordinates().get(activeCordinates.get(i+1)+1).contains(activeCordinates.get(i))){

                return true;
            }

            i++;
        }

        return false;
    }
private void setGridArray(){
    for (int i = 0; i <25 ; i++) {
        gridArray.add(new ArrayList<>());
    }

}


    /**
     *  Skapar ett 10*20 grid med hjälp av en arraylist med arraylist av rektanglar
     */
    private void gridCreator(){

        int grid_Width = 10;
        int grid_height = 20;
        int x = 0;
        int testt=0;

        for (int i = 0; i < grid_Width * grid_height ; i++) {
            Rectangle temp = new Rectangle(40,40);
            temp.setFill(Color.BLACK);
            temp.setStroke(Color.GREY);
            if (i %10 ==0 && i > 0){
                x++;
                testt=0;
            }
            gridArray.get(x).add(temp);




        tetrisGrid.addRow(x,gridArray.get(x).get(testt));

        testt++;
      }
    }

    private void addPiece(int x,int y){

        gridArray.get(y).get(x).setFill(pieces.getPieceColor(activePieceString));


    }
    private void removePiece(int x, int y){

        gridArray.get(y).get(x).setFill(Color.BLACK);

    }



    private void moveActivePieceRight()  {
        // kolla om eccupied piece är brevid
        Boolean possibleMove = true;



        for (int i = 0; i <activeCordinates.size() ; i++) {

            // om kordinaten höger om den aktiva kordinaten är upptagen
            if (tetrisModel.getOccupiedCordinates().get(activeCordinates.get(i+1)).contains(activeCordinates.get(i)+1)){
                possibleMove = false;
                System.out.println("NOT POSSIBLE MOVE");

            }
            i++;

        }


    for (int i = 0; i <activeCordinates.size() ; i++) {


            if (activeCordinates.get(i)==9){
            possibleMove=false;
            }
                i++;

        }
        if (possibleMove == true) {

            removePieces(activeCordinates);

            for (int i = 0; i <activeCordinates.size() ; i++) {
                activeCordinates.set(i,activeCordinates.get(i)+1);
                i++;
            }


            placePiece(activeCordinates);
            System.out.println("MOVE RIGHT");
        }


    }
    private void moveActivePieceLeft()  {
        Boolean possibleMove = true;

        for (int i = 0; i <activeCordinates.size() ; i++) {

            // om kordinaten höger om den aktiva kordinaten är upptagen
            if (tetrisModel.getOccupiedCordinates().get(activeCordinates.get(i+1)).contains(activeCordinates.get(i)-1)){
                possibleMove = false;
                System.out.println("NOT POSSIBLE MOVE");

            }
            i++;

        }

        for (int i = 0; i <activeCordinates.size() ; i++) {


            if (activeCordinates.get(i)==0){
                possibleMove=false;
            }
            i++;

        }
        if (possibleMove==true){
            removePieces(activeCordinates);

            for (int i = 0; i <activeCordinates.size() ; i++) {
                activeCordinates.set(i,activeCordinates.get(i)-1);
                i++;
            }


            placePiece(activeCordinates);
            System.out.println("MOVE RIGHT");
        }



    }

    private void initialValueActiveCordinates(Color color, int[] cordinates,int iteration){
        activeCordinates = new ArrayList<>();
        for (int i = 0; i < cordinates.length; i++) {



            activeCordinates.add(cordinates[i]);
            activeCordinates.add(cordinates[i+1]);
            i++;

        }
        System.out.println(activeCordinates+"INITIAL PIECE");

    }
    private void placePieceOneDown( ArrayList<Integer> cordinates){
        activeCordinates = new ArrayList<>();
        for (int i = 0; i < cordinates.size(); i++) {


            addPiece(cordinates.get(i),cordinates.get(i+1));

            activeCordinates.add(cordinates.get(i));
            activeCordinates.add(cordinates.get(i+1)+1);
            i++;

        }
        System.out.println(activeCordinates);

    }
    private void placePiece(ArrayList<Integer> cordinates){
        activeCordinates = new ArrayList<>();
        for (int i = 0; i < cordinates.size(); i++) {


            addPiece(cordinates.get(i),cordinates.get(i+1));

            activeCordinates.add(cordinates.get(i));
            activeCordinates.add(cordinates.get(i+1));
            i++;

        }
        System.out.println(activeCordinates);

    }
    private void removePieces(ArrayList<Integer> cordinates){

        for (int i = 0; i < cordinates.size(); i++) {
            if (cordinates.get(i+1)==0)
                removePiece(cordinates.get(i),cordinates.get(i+1));
            else {
                removePiece(cordinates.get(i),cordinates.get(i+1)-1);
            }
            i++;
        }
    }



    public void exitButton(ActionEvent actionEvent) {
    }

    public void onMousePressed(MouseEvent mouseEvent) throws InterruptedException {
        System.out.println("PAUSE");

        if (gamePaused==true){
            gamePaused = false;
            pausedText.setVisible(false);
            pauseButton.setVisible(false);

        }
        else{
            gamePaused=true;
            pausedText.setVisible(true);
            pauseButton.setVisible(true);
        }

    }
    public void handleKeyReleased(KeyEvent e) {
        switch (e.getCode()){

            case S:
                changeSpeed = true;
                gameSpeedMilliseconds = 250;
                System.out.println("RELEASED S");
                break;


            default:
                break;
        }


    }
    public void handleKeyPressed(KeyEvent e){
        System.out.println(e.getCode());
        switch (e.getCode()){
            case D:

                moveActivePieceRight();


                break;
            case A:
                moveActivePieceLeft();
                System.out.println("ERROR");
                break;
            case S:
                gameSpeedMilliseconds = 50;
                changeSpeed = true;

                break;


            default:
                break;
        }

    }
    public void pauseButtonAction(ActionEvent actionEvent) throws IOException {
        if (gamePaused==true){
            System.out.println("EXITT");
            stage = (Stage) pauseButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Scene scene = new Scene(root);


            stage.setScene(scene);
        }

    }


}