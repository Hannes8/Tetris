package sample;

import javafx.scene.paint.Color;

public class LPiece implements PiecesInterface {
    @Override
    public Color getPieceColor() {
        return Color.ORANGE;
    }

    @Override
    public int[] getPieceSize() {
        int [] pieceSize = {4,1,5,1,6,1,6,0};
        return pieceSize;
    }
}
