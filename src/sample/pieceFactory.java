package sample;

import javafx.scene.shape.Rectangle;

public class pieceFactory {

    TPiece test = new TPiece();
    public Rectangle getPiece(String piece){

        if (piece=="tpiece")
        return test.createPiece();
        else return null;
    }
    public int [] getPieceSize(){

        return test.getPieceSize();
    }
}
