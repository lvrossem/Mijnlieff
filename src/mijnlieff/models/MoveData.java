package mijnlieff.models;

import mijnlieff.pieces.PieceType;

//Data-transfer object
public class MoveData {
    private int row;
    private int column;
    private PieceType type;

    public MoveData(int row, int column, PieceType type) {
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
