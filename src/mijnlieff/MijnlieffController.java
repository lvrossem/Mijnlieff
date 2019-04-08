package mijnlieff;



import javafx.scene.control.Button;
import mijnlieff.server.Client;
import mijnlieff.models.MijnlieffBoard;
import mijnlieff.models.SidePieces;
import mijnlieff.pieces.*;

import java.util.ArrayList;
import java.util.HashMap;


public class MijnlieffController {

    private static HashMap<Character, PieceType> typePerChar = new HashMap<Character, PieceType>();
    static {
            typePerChar.put('o', PieceType.PULLER);
            typePerChar.put('@', PieceType.PUSHER);
            typePerChar.put('+', PieceType.TOREN);
            typePerChar.put('X', PieceType.LOPER);
    }

    private static String[] zetten = new String[] {
            "X T 0 0 @",
            "X T 0 1 o",
            "X T 1 0 +",
            "X T 1 1 o",
            "X T 2 0 @",
            "X T 2 1 +",
            "X T 3 0 X",
            "X T 3 1 X"
    };
    public Button backBut;
    public Button nextBut;
    public Button startBut;
    public Button endBut;
    public MijnlieffBoard board;
    public SidePieces whiteSide;
    public SidePieces blackSide;
    private Client client;





    public void initialize() {
        whiteSide.setColor(Color.WHITE);
        blackSide.setColor(Color.BLACK);
        backBut.setDisable(true);
        startBut.setDisable(true);
        board.setModels();
        blackSide.setModels();
        blackSide.setPieces();
        whiteSide.setModels();
        whiteSide.setPieces();
        client = new Client();
    }


    public void next() {
        int turn = board.getTurn();



        String current = client.getNewMove();
        System.out.println(current);

        Color color = Color.BLACK;
        if (turn % 2 == 0) {
            color = Color.WHITE;
        }

        PieceType type = typePerChar.get(current.charAt(8));

        int row = Character.getNumericValue(current.charAt(4));
        int column = Character.getNumericValue(current.charAt(6));

        board.addPiece(new Piece(color, type), row, column);

        if (turn % 2 == 0) {
            whiteSide.deletePieceImage(type);
        } else {
            blackSide.deletePieceImage(type);
        }




        backBut.setDisable(false);
        startBut.setDisable(false);
        nextBut.setDisable(board.getTurn() >= zetten.length);
        endBut.setDisable(board.getTurn() >= zetten.length);


    }


    public void back() {
        int turn = board.getTurn();

        ArrayList<Integer> p = board.getFieldsInOrder();
        int lastPlacedIndex = p.get(p.size()-1);
        PieceType type = board.getPieces().get(lastPlacedIndex).getType();
        board.deletePiece();
        if (turn % 2 != 0) {
            whiteSide.addPieceImage(type);
        } else {
            blackSide.addPieceImage(type);
        }

        backBut.setDisable(board.getTurn() <= 0);
        startBut.setDisable(board.getTurn() <= 0);
        nextBut.setDisable(false);
        endBut.setDisable(false);

    }

    public void end() {

        while (board.getTurn() < zetten.length) {
            next();

        }
    }

    public void start() {

        while (board.getTurn() != 0) {
            back();

        }
    }
}


