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


    private ArrayList<Coordinate> fieldsInOrder;
    private Piece[][] pieces;
    private ArrayList<Field> listeners;
    private ArrayList<String> codes;
    private int turn;

    public MijnlieffBoard() {
        pieces = new Piece[10][10];

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
        pieces[row][column] = piece;

        fieldsInOrder.add(new Coordinate(row, column));
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

    public static class Coordinate {
        private int row;
        private int column;

        public Coordinate(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }

    public ArrayList<Coordinate> getFieldsInOrder() {
        return fieldsInOrder;
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public void deletePiece() {
        Coordinate coordinate = fieldsInOrder.get(fieldsInOrder.size()-1);
        pieces[coordinate.getRow()][coordinate.getColumn()] = null;
        fireInvalidationEvent();
        fieldsInOrder.remove(fieldsInOrder.size() - 1);
        turn--;
    }

    public int[] getScore() {
        int[] points = new int[2];

        //telt de punten adhv de rijen
        for (int i = 0; i<10; i++) {
            int white = 0;
            int black = 0;
            for (int j = 0; j<10; j++) {
                Piece piece = pieces[i][j];
                if (piece != null) {
                    if (piece.getColor() == Color.WHITE) {
                        white++;
                    } else {
                        black++;
                    }
                }
            }

            if (white >= 3) {
                points[0] += white - 2;
            }

            if (black >= 3) {
                points[1] += black - 1;
            }

            white = 0;
            black = 0;

            for (int j = 0; j<10; j++) {
                Piece piece = pieces[j][i];
                if (piece != null) {
                    if (piece.getColor() == Color.WHITE) {
                        white++;
                    } else {
                        black++;
                    }
                }
            }

            if (white >= 3) {
                points[0] += white - 2;
            }

            if (black >= 3) {
                points[1] += black - 1;
            }

        }


        return points;
    }
}
