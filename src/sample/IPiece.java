package sample;

import javafx.scene.paint.Color;

public class IPiece implements PiecesInterface{
    @Override
    public Color getPieceColor() {
        return Color.TURQUOISE;
    }

    @Override
    public int[] getPieceSize() {
        int [] pieceSize = {4,0,5,0,6,0,7,0};
        return pieceSize;
    }
}
