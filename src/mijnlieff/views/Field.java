package mijnlieff.views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mijnlieff.models.MijnlieffModel;
import mijnlieff.models.MijnlieffBoard;
import mijnlieff.pieces.Piece;

import java.util.ArrayList;

public class Field extends ImageView {

    private MijnlieffBoard model;
    private int row;
    private int column;
    private Piece piece;



    public void setModel(MijnlieffBoard model) {
        this.model = model;
        register();


    }

    public void register() {
        model.registerListener(this);
    }

    public void setRow() {
        this.row = ((MijnlieffBoard) model).getRowIndex(this);

    }

    public void setColumn() {
        this.column = ((MijnlieffBoard) model).getColumnIndex(this);

    }

    public Piece getPiece() {
        return piece;
    }

    public void setAppearance() {
        Piece[][] pieces = model.getPieces();
        piece = pieces[row][column];
        if (piece != null) {
            setImage(new Image("/mijnlieff/img/" + piece.getColor().getColorString() + piece.getType().getUrl()));
        } else {
            setImage(new Image("/mijnlieff/img/red_square.png"));
        }
    }

    public void invalidationEvent() {
        setAppearance();
    }
}
