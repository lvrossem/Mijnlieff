package mijnlieff.views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mijnlieff.models.SidePieces;
import mijnlieff.pieces.Piece;

public class SideField extends ImageView {

    private SidePieces model;
    private int index;
    private Piece piece;

    public SideField() {
        setOnMouseClicked(e -> model.setSelected(this.getPiece()));
    }

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
        //stelt de padnaam van de juiste afbeelding samen adhv het kleur en het type
        if (piece != null) {
            setImage(new Image("mijnlieff/img/" + piece.getColor().getColorString() + piece.getType().getUrl()));
        } else {
            setImage(null);
        }
    }


}
