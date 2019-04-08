package mijnlieff.pieces;

public enum PieceType {
    LOPER("-loper.png"), PUSHER("-pusher.png"), TOREN("-toren.png"), PULLER("-puller.png");

    private String url;

    PieceType(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
