package sample;

import javafx.scene.paint.Color;

public class OPiece implements PiecesInterface {


    @Override
    public Color getPieceColor() {
        return Color.YELLOW;
    }

    @Override
    public int[] getPieceSize() {
        int [] pieceSize = {5,0,4,0,5,1,4,1};
        return pieceSize;
    }
}
