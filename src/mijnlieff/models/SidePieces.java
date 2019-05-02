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

//modelklasse die de kolommen aan de zijkant van het spelbord voorstelt
public class SidePieces extends VBox {

    //linkt elk stuktype met de plaats waar het staat in de kolom
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
    private ArrayList<Piece> pieces;
    private MijnlieffGameController controller;

    public SidePieces() {

        listeners = new ArrayList<>();
        pieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            SideField sideField = new SideField();
            sideField.setFitHeight(78);
            sideField.setFitWidth(78);
            sideField.setModel(this);
            sideField.setIndex(i);
            getChildren().add(sideField);

        }

    }

    //vult zichzelf op
    public void setPieces() {
        pieces.add(new Piece(color, PieceType.TOREN));
        pieces.add(new Piece(color, PieceType.TOREN));
        pieces.add(new Piece(color, PieceType.LOPER));
        pieces.add(new Piece(color, PieceType.LOPER));
        pieces.add(new Piece(color, PieceType.PUSHER));
        pieces.add(new Piece(color, PieceType.PUSHER));
        pieces.add(new Piece(color, PieceType.PULLER));
        pieces.add(new Piece(color, PieceType.PULLER));
        fireInvalidationEvent();
    }

    public void setSelected(Piece piece) {
        controller.setSelected(piece);

    }

    public void setController(MijnlieffGameController controller) {
        this.controller = controller;
    }

    public void registerListener(SideField listener) {
        listeners.add(listener);
    }

    public void setColor(Color color) {
        this.color = color;
        setPieces();
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

    //verwijdert een image als er een stuk geplaatst wordt
    public void deletePieceImage(PieceType type) {

        if (pieces.get(indexPerType.get(type)) != null) {

            pieces.set(indexPerType.get(type), null);
        } else {

            pieces.set(indexPerType.get(type) + 1, null);
        }

        fireInvalidationEvent();
    }


    //voegt een nieuwe image toe als er een stap terug wordt gegaan in het spel
    public void addPieceImage(PieceType type) {

        if (pieces.get(indexPerType.get(type)) == null) {

            pieces.set(indexPerType.get(type), new Piece(color, type));
        } else {

            pieces.set(indexPerType.get(type) + 1, new Piece(color, type));
        }

        fireInvalidationEvent();
    }
}
