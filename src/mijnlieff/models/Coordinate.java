package mijnlieff.models;

// Eenvoudige klasse om een coördinaat als een tuple van 2 ints voor te stellen
public class Coordinate {
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
