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
    @FXML
    public GridPane nextPieceGrid;
    public Button resumeButton;
    public Label scoreLabel;
    public Label nextLabel;
    public Label gameInformation;
    public Label levelLabel;


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

    private int gameSpeedMilliseconds;

    private pieceFactory pieces = new pieceFactory();

    private Boolean gamePaused = false;

    ArrayList <ArrayList<Rectangle>> gridArray=new ArrayList<>(999);

    Boolean changeSpeed = false;

    int x = 0;

    private  ArrayList <ArrayList<Rectangle>> gridArrayNext=new ArrayList<>(999);

    private int clearLineCount = 0;
    @FXML
    public void initialize() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        //AudioInputStream audioIn = AudioSystem.getAudioInputStream(gameSceneController.class.getResource("tetrismusic.wav"));
        //Clip clip = AudioSystem.getClip();
        //clip.open(audioIn);
      //  clip.start();
        pieces.setRandomPieceArray();


        /**
         *
         *
         * TODO
         * check if rotation is possible
         *
         *
         */





        tetrisModel.saveHighscore();

        setGridArray();
        gridCreator();

        gameSpeedMilliseconds = tetrisModel.getDifficultyInt();
        System.out.println(tetrisModel.getDifficultyInt() + " DIFFICULTY");

        System.out.println(tetrisModel.getLanguageString()+ " LANGUAGE");

        if (tetrisModel.getLanguageString().equals("swedish")){

            scoreLabel.setText("Poäng");
            nextLabel.setText("Nästa");
            pausedText.setText("Pausad");
            resumeButton.setText("Återuppta");
            pauseButton.setText("Lämna spel");
        }

        // gameInformation.setText("Language "+tetrisModel.getLanguageString()+": Difficulty "+tetrisModel.getDifficultyString());




            tetrisModel.initiateOccupiedCordinates();

            tetrisModel.setScore(0);

        System.out.println(tetrisModel.getScore());
        score.setText(Integer.toString(tetrisModel.getScore()));

        activePieceString = pieces.getRandomPiece().get(0);
        System.out.println(activePieceString+" PIECEEEEEEEEEEEEEEEEEEE");

        gameLoop();


    }
    private void gameLoop(){

        while (true){



            Timer fall = new Timer();
            TimerTask task = new TimerTask() {

                int count = 0;
                String piece;
                int level = 1;
                public void run() {

                    if (gamePaused == false) {


                        Platform.runLater(new Runnable() {

                            public void run() {
                                if (checkIfCordinateIsOccupied() || x == 19) {
                                    x = 0;

                                    tetrisModel.addOccupiedCordinates(activeCordinates);
                                    pieces.randomizeNewPiece();
                                    activePieceString = pieces.getRandomPiece().get(0);
                                }
                                if (x == 0) {


                                    initialValueActiveCordinates(pieces.getPieceColor(activePieceString), pieces.getPieceSize(activePieceString), x);
                                }

                                addPieceNextGrid();


                                    if (x > 0) {

                                    removePieces2(activeCordinates);
                                        placePieceOneDown(activeCordinates);
                                }
                                checkIfLineIsFull();
                                x++;


                                // om kordinaten är tagen eller det är den sista raden




                                // var tionde rad som blir borttagen så ökar leveln, svårhetsgraden
                                if (clearLineCount%10==0 && clearLineCount!=0){
                                    level++;
                                    levelLabel.setText("Level " + level);

                                    if (gameSpeedMilliseconds>100)
                                    gameSpeedMilliseconds =gameSpeedMilliseconds- 100;

                                    changeSpeed = true;

                                }



                                if (tetrisModel.getOccupiedCordinates().get(0).contains(5)) {

                                    System.out.println("GAME OVER");
                                }




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

    private void addPieceNextGrid() {
        for (int i = 0; i <12; i++) {
            for (int j = 0; j <4 ; j++) {
                gridArrayNext.get(i).get(j).setFill(Color.DARKBLUE);
                gridArrayNext.get(i).get(j).setStroke(Color.DARKBLUE);
            }

        }

        // ger de kordinaterna där som nästa piece har och ger den sin fill och stroke
        for (int i = 0; i < 8; i++) {
            gridArrayNext.get(pieces.getPieceSize(pieces.getRandomPiece().get(1))[i]-4).get(pieces.getPieceSize(pieces.getRandomPiece().get(1))[i+1]+1).setFill(pieces.getPieceColor(pieces.getRandomPiece().get(1)));
            gridArrayNext.get(pieces.getPieceSize(pieces.getRandomPiece().get(1))[i]-4).get(pieces.getPieceSize(pieces.getRandomPiece().get(1))[i+1]+1).setStroke(Color.GRAY);
            i++;

        }
        for (int i = 0; i < 8; i++) {
            gridArrayNext.get(pieces.getPieceSize(pieces.getRandomPiece().get(2))[i]).get(pieces.getPieceSize(pieces.getRandomPiece().get(2))[i+1]+1).setFill(pieces.getPieceColor(pieces.getRandomPiece().get(2)));
            gridArrayNext.get(pieces.getPieceSize(pieces.getRandomPiece().get(2))[i]).get(pieces.getPieceSize(pieces.getRandomPiece().get(2))[i+1]+1).setStroke(Color.GRAY);
            i++;

        }
        for (int i = 0; i < 8; i++) {
            gridArrayNext.get(pieces.getPieceSize(pieces.getRandomPiece().get(3))[i]+4).get(pieces.getPieceSize(pieces.getRandomPiece().get(3))[i+1]+1).setFill(pieces.getPieceColor(pieces.getRandomPiece().get(3)));
            gridArrayNext.get(pieces.getPieceSize(pieces.getRandomPiece().get(3))[i]+4).get(pieces.getPieceSize(pieces.getRandomPiece().get(3))[i+1]+1).setStroke(Color.GRAY);
            i++;

        }



    }

    private void checkIfLineIsFull() {

        for (int i = 0; i <21 ; i++) {

                if (tetrisModel.getOccupiedCordinates().get(i).size()==10){

                    removeLine(i);
                    clearLineCount ++;
                    score.setText(Integer.toString(tetrisModel.getScore() + 40));
                }
        }


    }

    private void removeLine(int row) {

        System.out.println("REMOVING LINE" + row);
        for (int i = 0; i < 10; i++) {

            removePiece(i,row);

        }

        // get score
        tetrisModel.removeOccupiedCordinatesRow(row);




        moveAllPiecesDown(row);
        //remove active pieces


    }

    /**
     * Tar in en rad och flyttar alla occupiedpieces över raden ett steg neråt
     * @param row
     */
    private void moveAllPiecesDown(int row) {



        // loopar igenom griden från den raden tas bort och uppåt


           for (int i = row-1; i !=0 ; i--) {
                      for (int j = 0; j <10 ; j++) {

                          if (tetrisModel.getOccupiedCordinates().get(i).contains(j)){

                             gridArray.get(i+1).get(j).setFill(gridArray.get(i).get(j).getFill());
                              removePiece(j,i);

                                tetrisModel.removeOccupiedCordinates(j,i);
                             tetrisModel.addSingleCordinateOccupiedCordinates(j,i+1);
                              }
                          }
                      }


         }

    /**
     * kollar om de aktiva kordinaterna redan är använda
     * @return
     */
    private Boolean checkIfCordinateIsOccupied(){
        for (int i = 0; i <activeCordinates.size() ; i++) {
            // om activeCordinates tillhör occupiedCordinates så blir det true

            if (tetrisModel.getOccupiedCordinates().get(activeCordinates.get(i+1)+1).contains(activeCordinates.get(i))){

                System.out.println(tetrisModel.getOccupiedCordinates().get(activeCordinates.get(i+1)+1)+ " POTATISMOS");
                for (int j = 0; j < 21 ; j++) {
                    System.out.println(tetrisModel.getOccupiedCordinates().get(j) );
                    System.out.println(j+ "ACTIVE ROW");

                }



                return true;
            }

            i++;
        }

        return false;
    }
private void setGridArray(){
    for (int i = 0; i <25 ; i++) {
        gridArray.add(new ArrayList<>());
        gridArrayNext.add(new ArrayList<>());
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

        x=0;
        testt = 0;
        for (int i = 0; i < 4*12; i++) {

            if (i%4==0 && i!=0){
                x++;
                testt = 0;
            }
            Rectangle temp1 = new Rectangle(40,40);




            gridArrayNext.get(x).add(temp1);
            nextPieceGrid.addRow(x,gridArrayNext.get(x).get(testt));
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

            removePieces2(activeCordinates);

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
            removePieces2(activeCordinates);

            for (int i = 0; i <activeCordinates.size() ; i++) {
                activeCordinates.set(i,activeCordinates.get(i)-1);
                i++;
            }


            placePiece(activeCordinates);

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


            if (cordinates.get(i+1)+1>=20){
            }
else {                addPiece(cordinates.get(i),cordinates.get(i+1)+1);
            }

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




    public void exitButton(ActionEvent actionEvent) {
    }

    public void onMousePressed(MouseEvent mouseEvent) throws InterruptedException {
        System.out.println("PAUSE");

        if (gamePaused==true){
            gamePaused = false;
            pausedText.setVisible(false);
            pauseButton.setVisible(false);
            resumeButton.setVisible(false);


        }
        else{
            gamePaused=true;
            pausedText.setVisible(true);
            pauseButton.setVisible(true);
            resumeButton.setVisible(true);
        }

    }
    public void handleKeyReleased(KeyEvent e) {
        switch (e.getCode()){

            case S:
                changeSpeed = true;
                gameSpeedMilliseconds = tetrisModel.getDifficultyInt();
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
     * formel tagen från http://tech.migge.io/2017/02/07/tetris-rotations/
     * x new = -y old
     * y new = x old
     *
     */
    private void rotatePiece() {
        ArrayList <Integer> xCordinates = new ArrayList<>();
        ArrayList <Integer> yCordinates = new ArrayList<>();
        ArrayList <Integer> Cordinates = new ArrayList<>();
        ArrayList <Integer> rotatedActiveCordinates = new ArrayList<>();

        // ger x cordinates och y cordinates sitt kordinat värde relativt till den första punkten i activecordinates
        for (int i = 0; i < activeCordinates.size(); i++) {
            if (i !=0){
               xCordinates.add(activeCordinates.get(0) - activeCordinates.get(i)) ;
               yCordinates.add(activeCordinates.get(1)- activeCordinates.get(i+1));
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
            rotatedActiveCordinates.add(activeCordinates.get(0)-Cordinates.get(i));
            rotatedActiveCordinates.add(activeCordinates.get(1)-Cordinates.get(i+1));
            i++;

        }
        removePieces2(activeCordinates);
        placePiece(rotatedActiveCordinates);

        //clearBoardFromInvalidPieces();

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


    public void resumeGame(ActionEvent actionEvent) throws InterruptedException {
        onMousePressed(null);
    }

    public void removePieces2(ArrayList <Integer> cordinates){

        for (int i = 0; i < cordinates.size(); i++) {
            if (cordinates.get(i+1)==0)
                removePiece(cordinates.get(i),cordinates.get(i+1));
            else {
                removePiece(cordinates.get(i),cordinates.get(i+1));
            }
            i++;
        }
    }

}