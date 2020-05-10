package sample;

import javafx.scene.paint.Color;

public class ZPiece implements PiecesInterface {
    @Override
    public Color getPieceColor() {
        return Color.RED;
    }

    @Override
    public int[] getPieceSize() {
        int [] pieceSize = {4,0,5,0,5,1,6,1};
        return pieceSize;
    }
}
