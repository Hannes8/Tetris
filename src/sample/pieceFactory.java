package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class pieceFactory {
    private ArrayList <String> randomPieceArray = new ArrayList<>();

    private TPiece tPiece = new TPiece();
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
    public ArrayList<String> getRandomPiece(){



        return randomPieceArray;


    }
    public void randomizeNewPiece()
    {String [] pieceNameArray={"tpiece","opiece"};
        int random =(int)(pieceNameArray.length * Math.random());
        randomPieceArray.remove(0);
        randomPieceArray.add(pieceNameArray[random]);
    }
    public void setRandomPieceArray(){
        for (int i = 0; i < 4; i++) {
            String [] pieceNameArray={"tpiece","opiece"};
            int random =(int)(pieceNameArray.length * Math.random());
            randomPieceArray.add(pieceNameArray[random]);
        }

    }
}
