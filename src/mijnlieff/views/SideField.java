package mijnlieff.views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mijnlieff.models.MijnlieffModel;
import mijnlieff.models.SidePieces;
import mijnlieff.pieces.Piece;

import java.util.ArrayList;

public class SideField extends ImageView {

    private SidePieces model;
    private int index;
    private Piece piece;



    public void setModel(SidePieces model) {
        this.model = model;
        register();
    }

    public void register() {
        model.registerListener(this);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Piece getPiece() {
        return piece;
    }

    public void invalidationEvent() {
        setAppearance();
    }

    public void setAppearance() {
        piece = model.getPieces().get(index);

        if (piece != null) {
            setImage(new Image("mijnlieff/img/" + piece.getColor().getColorString() + piece.getType().getUrl()));
        } else {
            setImage(null);
        }
    }


}
