package mijnlieff.models;


import javafx.scene.Node;
import javafx.scene.layout.VBox;
import mijnlieff.controllers.MijnlieffGameController;
import mijnlieff.views.SideField;
import mijnlieff.pieces.Color;
import mijnlieff.pieces.Piece;
import mijnlieff.pieces.PieceType;
import java.util.ArrayList;
import java.util.HashMap;

// Modelklasse die de kolommen aan de zijkant van het spelbord voorstelt
public class SidePieces extends VBox {

    // Linkt elk stuktype met de plaats waar het staat in de kolom
    private static HashMap<PieceType, Integer> indexPerType = new HashMap<PieceType, Integer>();
    static {
        indexPerType.put(PieceType.TOREN, 0);
        indexPerType.put(PieceType.LOPER, 2);
        indexPerType.put(PieceType.PUSHER, 4);
        indexPerType.put(PieceType.PULLER, 6);
    }

    private Color color;
    private Piece selected;
    private ArrayList<SideField> listeners;
    private Piece[] pieces;
    private MijnlieffGameController controller;

    public SidePieces() {
        listeners = new ArrayList<>();
        pieces = new Piece[8];
        for (int i = 0; i < 8; i++) {
            SideField sideField = new SideField();
            sideField.setFitHeight(78);
            sideField.setFitWidth(78);
            sideField.setModel(this);
            sideField.setIndex(i);
            getChildren().add(sideField);
        }
    }

    // Vult zichzelf op
    public void setPieces() {
        pieces[0] = new Piece(color, PieceType.TOREN);
        pieces[1] = new Piece(color, PieceType.TOREN);
        pieces[2] = new Piece(color, PieceType.LOPER);
        pieces[3] = new Piece(color, PieceType.LOPER);
        pieces[4] = new Piece(color, PieceType.PUSHER);
        pieces[5] = new Piece(color, PieceType.PUSHER);
        pieces[6] = new Piece(color, PieceType.PULLER);
        pieces[7] = new Piece(color, PieceType.PULLER);
        fireInvalidationEvent();
    }

    public MijnlieffGameController getController() {
        return controller;
    }

    public void setSelected(Piece piece) {
        controller.setSelected(piece);
        System.out.println(piece.getType().getUrl());
    }

    public void setController(MijnlieffGameController controller) {
        this.controller = controller;
        for (Node node: getChildren()) {
            ((SideField) node).setModel(this);
        }
    }

    public void registerListener(SideField listener) {
        listeners.add(listener);
    }

    public void setColor(Color color) {
        this.color = color;
        setPieces();
    }

    public Piece[] getPieces() {
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

    // Verwijdert een image als er een stuk geplaatst wordt
    public void deletePieceImage(PieceType type) {

        if (pieces[indexPerType.get(type)] != null) {

            pieces[indexPerType.get(type)] = null;
        } else {

            pieces[indexPerType.get(type) + 1] = null;
        }

        fireInvalidationEvent();
    }

    // Voegt een nieuwe image toe als er een stap terug wordt gegaan in het spel
    public void addPieceImage(PieceType type) {

        if (pieces[indexPerType.get(type)] == null) {

            pieces[indexPerType.get(type)] = new Piece(color, type);
        } else {

            pieces[indexPerType.get(type) + 1] = new Piece(color, type);
        }

        fireInvalidationEvent();
    }
}
