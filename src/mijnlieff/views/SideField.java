package mijnlieff.views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mijnlieff.models.MijnlieffModel;
import mijnlieff.models.SidePieces;
import mijnlieff.pieces.Piece;

import java.util.ArrayList;

public class SideField extends ImageView implements MijnlieffListener {

    private SidePieces model;
    private int index;



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

    public void invalidationEvent() {
        setAppearance();
    }

    public void setAppearance() {
        ArrayList<Piece> pieces = model.getPieces();

        if (pieces.get(index) != null) {
            setImage(new Image("mijnlieff/img/" + pieces.get(index).getColor().getColorString() + pieces.get(index).getType().getUrl()));
        } else {
            setImage(null);
        }
    }


}
