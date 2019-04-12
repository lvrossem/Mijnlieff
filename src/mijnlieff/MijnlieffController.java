package mijnlieff;



import javafx.scene.control.Button;
import mijnlieff.server.Client;
import mijnlieff.models.MijnlieffBoard;
import mijnlieff.models.SidePieces;
import mijnlieff.pieces.*;

import java.util.ArrayList;
import java.util.HashMap;



public class MijnlieffController {


    public Button backBut;
    public Button nextBut;
    public Button startBut;
    public Button endBut;
    public MijnlieffBoard board;
    public SidePieces whiteSide;
    public SidePieces blackSide;
    private Client client;

    private static HashMap<Character, PieceType> typePerChar = new HashMap<Character, PieceType>();
    static {
        typePerChar.put('o', PieceType.PULLER);
        typePerChar.put('@', PieceType.PUSHER);
        typePerChar.put('+', PieceType.TOREN);
        typePerChar.put('X', PieceType.LOPER);
    }




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
    }

    public void makeConnection(String server, int poort) {
        client = new Client(server, poort);
        String message = client.getNewMove();
        while (message.contains("F")) {
            board.addCode(message);
            message = client.getNewMove();
        }
        board.addCode(message);
        client.closeConnection();

    }


    public void next() {
        int turn = board.getTurn();

        ArrayList<String> codes = board.getCodes();
        String current = codes.get(board.getTurn());

        Color color = Color.BLACK;
        if (turn % 2 == 0) {
            color = Color.WHITE;
        }

        PieceType type = typePerChar.get(current.charAt(8));

        board.addPiece(current);

        if (turn % 2 == 0) {
            whiteSide.deletePieceImage(type);
        } else {
            blackSide.deletePieceImage(type);
        }

        if (current.contains("T")) {
            nextBut.setDisable(true);
            endBut.setDisable(true);
        }

        backBut.setDisable(false);
        startBut.setDisable(false);

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

        while (board.getTurn() < board.getCodes().size()) {
            next();

        }
    }

    public void start() {

        while (board.getTurn() != 0) {
            back();

        }
    }
}


