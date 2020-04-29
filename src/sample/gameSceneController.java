package sample;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class gameSceneController {

    ArrayList <Integer> activeCordinates = new ArrayList<>(50);

    TetrisModel tetrisModel = new TetrisModel();
    @FXML
    Label score;

    @FXML
    GridPane tetrisGrid = new GridPane();

    @FXML
    BorderPane borderPane;

    private pieceFactory pieces = new pieceFactory();

    ArrayList <ArrayList<Rectangle>> gridArray=new ArrayList<>(999);

    Rectangle temp = new Rectangle(40,40);

    int x3 = 0;

    @FXML
    public void initialize(){
            setGridArray();
            gridCreator();
            tetrisModel.initiateOccupiedCordinates();
        int[][] myArray = { {0,1,2,3}, {3,2,1,0}, {3,5,6,1}, {3,8,3,4} };






        tetrisModel.setScore(555);

        System.out.println(tetrisModel.getScore());
        score.setText(Integer.toString(tetrisModel.getScore()));


    while (true){
        Timer fall = new Timer();
        TimerTask task = new TimerTask() {
            int x= 0;
            String piece;
            public void run() {

                Platform.runLater(new Runnable() {
                    public void run() {


                        //if (pieces.getPieceSize()==tetrisModel.setOccupiedCordinates();)
                        // random piece


                        if (x>0)
                            removePieces(pieces.getPieceSize(),x);


                        System.out.println("HEJEHEJEHJE");
                        placePiece(pieces.getPiece("tpiece"),pieces.getPieceSize(),x);



                        x++;
                        if (x==19){

                            //pieces.getPieceSize().
                            tetrisModel.addOccupiedCordinates(activeCordinates);

                        }

                        if (tetrisModel.getOccupiedCordinates().get(x).contains(5) || x==19){
                            x=0;

                            System.out.println("DSFJKSLKFSJHFKJLSFHLJKSFKLSDHSKFSHKFDJSHFKUJSDFKJHLFKJHD");
                        }


                        score.setText(Integer.toString(tetrisModel.getScore()+x));
                    }
                });
            }
        };
        fall.schedule(task, 0, 250);


        break;
    }

    }
public void setGridArray(){
    for (int i = 0; i <25 ; i++) {
        gridArray.add(new ArrayList<>());
    }

}

public void pieceFall(){
    for (int i = 0; i <20 ; i++) {

        if (i>0){

        }

    }

}

    /**
     *  Skapar ett 10*20 grid med hjälp av en arraylist med arraylist av rektanglar
     */
    public void gridCreator(){

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
        gridArray.get(2).get(3).setFill(Color.RED);


    }
    public void test(){

    }

    public void addPiece(int x,int y,Rectangle piece){

        gridArray.get(y).get(x).setFill(Color.GREEN);

       // temp = new Rectangle(40,40);
        //temp.setFill(Color.GREEN);
        //temp.setStroke(Color.GREY);
        //tetrisGrid.add(temp,x,y);

    }
    public void removePiece(int x, int y){

        gridArray.get(y).get(x).setFill(Color.BLACK);

    }


    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {

        System.out.println("gdggdg");
        if (keyEvent.getCode().equals(KeyCode.S)){

            System.out.println("GJ MAN");
                    int [] hej = {4,0,4,1,4,2,4,3};
           // placePiece(new Rectangle(40,40),hej );

        }
        System.out.println("fdfddsfdsfsfdsfsfsfsfsfdsfsfdsdfsdfsf");


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }


    }

    public void yyy(){

    }

    public void hehe(ActionEvent actionEvent) {
        System.out.println("gdgdgfdgdgfd");


    }

    public void placePiece(Rectangle rect, int[] cordinates,int iteration){
            activeCordinates = new ArrayList<>();
        for (int i = 0; i < cordinates.length; i++) {


            addPiece(cordinates[i],cordinates[i+1]+iteration,rect);
            activeCordinates.add(cordinates[i]);
            activeCordinates.add(cordinates[i+1]+iteration);
            i++;

        }
        System.out.println(activeCordinates);

    }
    public void removePieces(int[] cordinates,int iteration){

        for (int i = 0; i < cordinates.length; i++) {


            removePiece(cordinates[i],cordinates[i+1]+iteration-1);
            i++;
        }


    }

}