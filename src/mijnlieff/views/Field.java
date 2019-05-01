package mijnlieff.views;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mijnlieff.models.MijnlieffBoard;
import mijnlieff.pieces.Piece;

//een view die een vakje op het spelbord voorstelt
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

    public void setRow(int row) {
        this.row = row;

    }

    public void setColumn(int column) {
        this.column = column;

    }

    public int getRow() {
        return row;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setAppearance() {
        Piece[][] pieces = model.getPieces();
        piece = pieces[row][column];
        //stelt de padnaam van de juiste afbeelding samen adhv het kleur en het type
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
