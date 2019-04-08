package mijnlieff.models;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import mijnlieff.views.Field;
import mijnlieff.pieces.Piece;
import mijnlieff.views.MijnlieffListener;

import java.util.ArrayList;

public class MijnlieffBoard extends GridPane {

    private ObservableList<Node> children;
    private ArrayList<Integer> fieldsInOrder;
    private ArrayList<Piece> pieces;
    private ArrayList<Field> listeners;
    private int turn;

    public MijnlieffBoard() {
        pieces = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            pieces.add(null);
        }
        fieldsInOrder = new ArrayList<>();
        listeners = new ArrayList<>();
        children = getChildren();
        turn = 0;
    }

    public void setModels() {
        children = getChildren();

        for (Node node: children) {

            ((Field) node).setModel(this);

            ((Field) node).setColumn();
            ((Field) node).setRow();

        }
    }

    public void registerListener(Field listener) {
        listeners.add(listener);
    }

    public void addPiece(Piece piece, int row, int column) {
        pieces.set(4*row+column, piece);

        fieldsInOrder.add(4*row + column);
        fireInvalidationEvent();
        turn++;
    }

    public int getTurn() {
        return turn;
    }

    public void fireInvalidationEvent() {
        for (MijnlieffListener listener: listeners) {
            listener.invalidationEvent();

        }
    }

    public ArrayList<Integer> getFieldsInOrder() {
        return fieldsInOrder;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public void deletePiece() {
        int coordinaat = fieldsInOrder.get(fieldsInOrder.size()-1);
        pieces.set(coordinaat, null);
        fireInvalidationEvent();
        fieldsInOrder.remove(fieldsInOrder.size() - 1);
        turn--;
    }


}
