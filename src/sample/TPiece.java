package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class TPiece implements PiecesInterface{



    public Color getPieceColor() {
        return Color.PURPLE;
    }

    public int[] getPieceSize() {

        // start kordinaterna för klossen x,y för 4 punkter
        int [] pieceSize = {5,0,4,1,5,1,6,1};
        return pieceSize;
    }
}
