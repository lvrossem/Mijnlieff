package mijnlieff.views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mijnlieff.controllers.MijnlieffGameController;
import mijnlieff.models.SidePieces;
import mijnlieff.pieces.Piece;

public class SideField extends ImageView {

    private SidePieces model;
    private int index;

    public void setModel(SidePieces model) {
        this.model = model;
        register();
        if (model.getController() != null) {
            setOnMouseClicked(e -> model.setSelected(model.getPieces()[index]));
        }
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
        Piece piece = model.getPieces()[index];
        // Stelt de padnaam van de juiste afbeelding samen adhv het kleur en het type
        if (piece != null) {
            setImage(new Image("mijnlieff/img/" + piece.getColor().getColorString() + piece.getType().getUrl()));
        } else {
            setImage(null);
        }
    }
}
