package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class pieceFactory {

    TPiece tPiece = new TPiece();
    OPiece opiece = new OPiece();
    public Color getPieceColor(String piece){
        switch (piece){

            case "tpiece":
                return tPiece.getPieceColor();
            case "opiece":
                return opiece.getPieceColor();

        }



        return null;
    }
    public int [] getPieceSize(String piece){
        switch (piece){
            case "tpiece":
                return tPiece.getPieceSize();
            case "opiece":
                return opiece.getPieceSize();


        }


        return tPiece.getPieceSize();
    }
    public String getRandomPiece(){
        String [] pieceNameArray={"tpiece","opiece"};
        int random =(int)(pieceNameArray.length * Math.random());
        System.out.println(random+"RANDOM");
        return pieceNameArray[random];


    }
}
