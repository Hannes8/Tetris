package sample;

import javafx.scene.paint.Color;

public class JPiece implements PiecesInterface {
    @Override
    public Color getPieceColor() {
        return Color.BLUE;
    }

    @Override
    public int[] getPieceSize() {
        int [] pieceSize = {4,0,4,1,5,1,6,1};
        return pieceSize;
    }
}
