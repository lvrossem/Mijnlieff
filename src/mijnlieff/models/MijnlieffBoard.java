package mijnlieff.models;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import mijnlieff.pieces.Color;
import mijnlieff.pieces.PieceType;
import mijnlieff.views.Field;
import mijnlieff.pieces.Piece;

import java.util.ArrayList;
import java.util.HashMap;

public class MijnlieffBoard extends GridPane {

    private static HashMap<Character, PieceType> typePerChar = new HashMap<Character, PieceType>();
    static {
        typePerChar.put('o', PieceType.PULLER);
        typePerChar.put('@', PieceType.PUSHER);
        typePerChar.put('+', PieceType.TOREN);
        typePerChar.put('X', PieceType.LOPER);
    }


    private ArrayList<Integer> fieldsInOrder;
    private ArrayList<Piece> pieces;
    private ArrayList<Field> listeners;
    private ArrayList<String> codes;
    private int turn;

    public MijnlieffBoard() {
        pieces = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            pieces.add(null);
        }
        fieldsInOrder = new ArrayList<>();
        listeners = new ArrayList<>();
        codes = new ArrayList<>();

        turn = 0;
    }

    public void addCode(String code) {
        codes.add(code);
    }

    public ArrayList<String> getCodes() {
        return codes;
    }

    public void setModels() {
        ObservableList<Node> children = getChildren();

        for (Node node: children) {

            ((Field) node).setModel(this);

            ((Field) node).setColumn();
            ((Field) node).setRow();

        }
    }

    public void registerListener(Field listener) {
        listeners.add(listener);
    }

    public void addPiece(String code) {
        Color color = Color.BLACK;
        if (turn % 2 == 0) {
            color = color.WHITE;
        }

        int row = Character.getNumericValue(code.charAt(4));
        int column = Character.getNumericValue(code.charAt(6));

        Piece piece = new Piece(color, typePerChar.get(code.charAt(8)));
        pieces.set(4*row + column, piece);

        fieldsInOrder.add(4*row + column);
        fireInvalidationEvent();
        turn++;
    }

    public int getTurn() {
        return turn;
    }

    public void fireInvalidationEvent() {
        for (Field listener: listeners) {
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
