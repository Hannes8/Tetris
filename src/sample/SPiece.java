package sample;

import javafx.scene.paint.Color;

public class SPiece implements PiecesInterface{
    @Override
    public Color getPieceColor() {
        return Color.GREEN;
    }

    @Override
    public int[] getPieceSize() {
        int [] pieceSize = {4,1,5,1,5,0,6,0};
        return pieceSize;
    }
}
