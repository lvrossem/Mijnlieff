package mijnlieff.models;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import mijnlieff.views.SideField;
import mijnlieff.pieces.Color;
import mijnlieff.pieces.Piece;
import mijnlieff.pieces.PieceType;

import java.util.ArrayList;
import java.util.HashMap;


public class SidePieces extends VBox {

    private static HashMap<PieceType, Integer> indexPerType = new HashMap<PieceType, Integer>();
    static {
        indexPerType.put(PieceType.TOREN, 0);
        indexPerType.put(PieceType.LOPER, 2);
        indexPerType.put(PieceType.PUSHER, 4);
        indexPerType.put(PieceType.PULLER, 6);
    }

    private Color color;

    private ArrayList<SideField> listeners;
    private ArrayList<Piece> pieces;

    public SidePieces() {

        listeners = new ArrayList<>();
        pieces = new ArrayList<>();


    }

    public void setPieces() {
        pieces.add(new Piece(color, PieceType.TOREN));
        pieces.add(new Piece(color, PieceType.TOREN));
        pieces.add(new Piece(color, PieceType.LOPER));
        pieces.add(new Piece(color, PieceType.LOPER));
        pieces.add(new Piece(color, PieceType.PUSHER));
        pieces.add(new Piece(color, PieceType.PUSHER));
        pieces.add(new Piece(color, PieceType.PULLER));
        pieces.add(new Piece(color, PieceType.PULLER));
    }

    public void registerListener(SideField listener) {
        listeners.add(listener);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public void fireInvalidationEvent() {
        for (SideField listener: listeners) {
            listener.invalidationEvent();
        }
    }

    public void setModels() {
        int index = 0;
        for (Node node: getChildren()) {
            ((SideField) node).setModel(this);
            ((SideField) node).setIndex(index);
            index++;
        }
    }

    public void deletePieceImage(PieceType type) {

        if (pieces.get(indexPerType.get(type)) != null) {

            pieces.set(indexPerType.get(type), null);
        } else {

            pieces.set(indexPerType.get(type) + 1, null);
        }

        fireInvalidationEvent();
    }



    public void addPieceImage(PieceType type) {

        if (pieces.get(indexPerType.get(type)) == null) {

            pieces.set(indexPerType.get(type), new Piece(color, type));
        } else {

            pieces.set(indexPerType.get(type) + 1, new Piece(color, type));
        }

        fireInvalidationEvent();
    }
}
