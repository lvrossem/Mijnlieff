package mijnlieff.pieces;

public enum Color {
    WHITE("wit"), BLACK("zwart");

    private String colorString;

    Color(String colorString) {
        this.colorString = colorString;
    }

    public String getColorString() {
        return colorString;
    }
}
