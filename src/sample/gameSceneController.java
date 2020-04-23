package sample;


import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class gameSceneController {
    @FXML
    GridPane tetrisGrid;

    @FXML
    Rectangle rect;


    public void initialize(){

            gridCreator();



}
    public void gridCreator(){

        Rectangle gridRectangle = new Rectangle(40,40);
        gridRectangle.setFill(Color.GRAY);
        gridRectangle.setStroke(Color.GREEN);

        int grid_Width = 10;
        int grid_height = 20;
        int x = 0;

        for (int i = 0; i < grid_Width * grid_height ; i++) {
            Rectangle temp = new Rectangle(40,40);
            temp.setFill(Color.BLACK);
            temp.setStroke(Color.GREY);
            if (i %10 ==0 && i > 0){
                x++;
            }
            tetrisGrid.addRow(x,temp);
        }

        addPiece();

    }
    public void addPiece(){
        Rectangle temp = new Rectangle(40,40);
        temp.setFill(Color.GREEN);
        temp.setStroke(Color.GREY);
        tetrisGrid.add(temp,5,5);

    }
}