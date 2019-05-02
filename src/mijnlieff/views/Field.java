package mijnlieff.views;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mijnlieff.models.MijnlieffBoard;
import mijnlieff.pieces.Piece;

//een view die een vakje op het spelbord voorstelt
public class Field extends ImageView {

    private MijnlieffBoard model;
    private int row;
    private int column;

    public void setModel(MijnlieffBoard model) {
        this.model = model;
        register();
        setOnMouseClicked(e -> model.addSelected(row, column));

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

    public int getColumn() {
        return column;
    }



    public void setAppearance() {
        Piece[][] pieces = model.getPieces();
        Piece piece = pieces[row][column];
        //stelt de padnaam van de juiste afbeelding samen adhv het kleur en het type
        if (piece != null) {
            setImage(new Image("/mijnlieff/img/" + piece.getColor().getColorString() + piece.getType().getUrl()));
        } else {
            setImage(new Image("/mijnlieff/img/empty_square.jpg"));
        }
    }

    public void invalidationEvent() {
        setAppearance();
    }
}
