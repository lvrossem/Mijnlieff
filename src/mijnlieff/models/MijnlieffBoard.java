package mijnlieff.models;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import mijnlieff.controllers.MijnlieffGameController;
import mijnlieff.pieces.Color;
import mijnlieff.pieces.PieceType;
import mijnlieff.views.Field;
import mijnlieff.pieces.Piece;
import java.util.ArrayList;
import java.util.HashMap;

//modelklasse die het spelbord voorstelt
public class MijnlieffBoard extends GridPane {

    //linkt elk symbool dat de server in viewermodus kan doorsturen aan het corresponderende stuktype
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
    private MijnlieffGameController controller;
    private LastMoveData lastPlaced;

    public MijnlieffBoard() {
        pieces = new Piece[11][11];

        fieldsInOrder = new ArrayList<>();
        listeners = new ArrayList<>();
        codes = new ArrayList<>();

        turn = 0;


    }



    public void setModels() {
        ObservableList<Node> children = getChildren();

        int i = 0;
        for (Node node: children) {

            ((Field) node).setModel(this);
            ((Field) node).setRow(i/4);
            ((Field) node).setColumn(i%4);
            i++;
        }
        fireInvalidationEvent();
    }



    public void setController(MijnlieffGameController controller) {
        this.controller = controller;
    }

    public boolean sameRowOrColumn(int row, int column) {
        return lastPlaced.getRow() == row || lastPlaced.getColumn() == column;
    }

    public boolean notTouching(int row, int column) {
        int lpc = lastPlaced.getColumn();
        int lpr = lastPlaced.getRow();

        return Math.abs(lpr-row) > 1 || Math.abs(lpc-column) > 1;
    }

    public boolean sameDiagonal(int row, int column) {
        int lpc = lastPlaced.getColumn();
        int lpr = lastPlaced.getRow();

        return lpc - lpr == column-row || lpc + lpr == row + column;
    }

    public void addSelected(int row, int column) {

        if (controller.getSelected() != null && pieces[row][column] == null) {
            Piece p = controller.getSelected();
            if (lastPlaced != null) {

                if (lastPlaced.getType() == PieceType.TOREN) {
                    if (sameRowOrColumn(row, column)) {
                        lastPlaced = new LastMoveData(row, column, pieces[row][column].getType());
                        updateBoard(row, column, p);
                    } else {
                        System.out.println("Ongeldig");
                    }

                } else if (lastPlaced.getType() == PieceType.LOPER) {
                    if (sameDiagonal(row, column)) {
                        lastPlaced = new LastMoveData(row, column, pieces[row][column].getType());
                        updateBoard(row, column, p);
                    } else {
                        System.out.println("Ongeldig");
                    }
                } else if (lastPlaced.getType() == PieceType.PULLER) {
                    if (!notTouching(row, column)) {
                        lastPlaced = new LastMoveData(row, column, pieces[row][column].getType());
                        updateBoard(row, column, p);
                    } else {
                        System.out.println("Ongeldig");
                    }
                } else if (lastPlaced.getType() == PieceType.PUSHER) {
                    if (notTouching(row, column)) {
                        lastPlaced = new LastMoveData(row, column, pieces[row][column].getType());
                        updateBoard(row, column, p);
                    } else {
                        System.out.println("Ongeldig");
                    }
                }
            } else {
                pieces[row][column] = p;
                lastPlaced = new LastMoveData(row, column, pieces[row][column].getType());
                updateBoard(row, column, p);
            }



        }
    }

    public void updateBoard(int row, int column, Piece p) {
        pieces[row][column] = p;
        fireInvalidationEvent();
        controller.setSelectedNull();
        turn++;
    }

    public void addCode(String code) {
        codes.add(code);
    }

    public ArrayList<String> getCodes() {
        return codes;
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

        if (controller instanceof MijnlieffGameController) {
            lastPlaced = new LastMoveData(row, column, pieces[row][column].getType());
        }

        Piece piece = new Piece(color, typePerChar.get(code.charAt(8)));
        pieces[row][column] = piece;

        fieldsInOrder.add(new Coordinate(row, column));
        lastPlaced = new LastMoveData(row, column, piece.getType());
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

    public LastMoveData getLastPlaced() {
        return lastPlaced;
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

    //Data-transfer object
    public class LastMoveData {
        private int row;
        private int column;
        private PieceType type;

        public LastMoveData(int row, int column, PieceType type) {
            this.column = column;
            this.row = row;
            this.type = type;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public PieceType getType() {
            return type;
        }
    }
}
