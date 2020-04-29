package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class TPiece implements PiecesInterface{



    public Rectangle createPiece() {
        Rectangle TPiece = new Rectangle(40,40);
        TPiece.setFill(Color.PURPLE);
        TPiece.setStroke(Color.GREY);


        return TPiece;
    }

    public int[] getPieceSize() {

        // kordinaterna för klossen x,y för 4 punkter
        int [] pieceSize = {5,0,4,1,5,1,6,1};
        return pieceSize;
    }
}
