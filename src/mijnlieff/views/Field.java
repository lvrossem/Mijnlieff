package mijnlieff.views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mijnlieff.models.MijnlieffModel;
import mijnlieff.models.MijnlieffBoard;
import mijnlieff.pieces.Piece;

import java.util.ArrayList;

public class Field extends ImageView implements MijnlieffListener {

    private MijnlieffBoard model;
    private int row;
    private int column;

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

    public void setAppearance() {
        ArrayList<Piece> pieces = model.getPieces();
        if (pieces.get(row*4 + column) != null) {
            setImage(new Image("mijnlieff/img/" + pieces.get(row * 4 + column).getColor().getColorString() + pieces.get(row * 4 + column).getType().getUrl()));
        } else
            setImage(new Image("mijnlieff/img/red_square.png"));
    }

    public void invalidationEvent() {
        setAppearance();
    }
}
