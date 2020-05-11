package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class pieceFactory {
    private ArrayList <String> randomPieceArray = new ArrayList<>();

    private TPiece tpiece = new TPiece();
    private OPiece opiece = new OPiece();
    private IPiece ipiece = new IPiece();
    private ZPiece zpiece = new ZPiece();
    private JPiece jpiece = new JPiece();
    private LPiece lpiece = new LPiece();
    private SPiece spiece = new SPiece();

    String [] pieceNameArray={"tpiece","opiece","ipiece","zpiece","jpiece","lpiece","spiece"};

    public Color getPieceColor(String piece){
        switch (piece){

            case "tpiece":
                return tpiece.getPieceColor();
            case "opiece":
                return opiece.getPieceColor();
            case "ipiece":
                return ipiece.getPieceColor();
            case "zpiece":
                return zpiece.getPieceColor();
            case "jpiece":
                return jpiece.getPieceColor();
            case "lpiece":
                return lpiece.getPieceColor();
            case  "spiece":
                return spiece.getPieceColor();

        }



        return null;

    }

    public int [] getPieceSize(String piece){
        switch (piece){
            case "tpiece":
                return tpiece.getPieceSize();
            case "opiece":
                return opiece.getPieceSize();
           case "ipiece":
                return ipiece.getPieceSize();
            case "zpiece":
                return zpiece.getPieceSize();
            case "jpiece":
                return jpiece.getPieceSize();
            case "lpiece":
                return lpiece.getPieceSize();
            case "spiece":
                return spiece.getPieceSize();
        }


        return null;
    }
    public ArrayList<String> getRandomPiece(){



        return randomPieceArray;


    }

    public void randomizeNewPiece(){
        int random =(int)(pieceNameArray.length * Math.random());
        randomPieceArray.remove(0);
        randomPieceArray.add(pieceNameArray[random]);
    }

    public void setRandomPieceArray(){
        for (int i = 0; i < 4; i++) {
            int random =(int)(pieceNameArray.length * Math.random());
            randomPieceArray.add(pieceNameArray[random]);
        }

    }
}
