package mijnlieff.pieces;

//stelt de kleur van een stuk voor
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
