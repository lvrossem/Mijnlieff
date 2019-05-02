package mijnlieff.controllers;


import javafx.scene.control.Button;
import mijnlieff.models.Coordinate;
import mijnlieff.server.Client;
import mijnlieff.models.MijnlieffBoard;
import mijnlieff.models.SidePieces;
import mijnlieff.pieces.*;
import java.util.ArrayList;
import java.util.HashMap;


//controllerklasse voor de viewerversie van mijnlieff (als het opstart zoals in deel 1)
public class MijnlieffViewerController extends MijnlieffController {


    public Button backBut;
    public Button nextBut;
    public Button startBut;
    public Button endBut;
    public MijnlieffBoard board;
    public SidePieces whiteSide;
    public SidePieces blackSide;
    private String configuration;

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

    //linkt alle karakters aan het corresponderende stuktype
    private static HashMap<Character, PieceType> typePerChar = new HashMap<Character, PieceType>();
    static {
        typePerChar.put('o', PieceType.PULLER);
        typePerChar.put('@', PieceType.PUSHER);
        typePerChar.put('+', PieceType.TOREN);
        typePerChar.put('X', PieceType.LOPER);
    }

    public void viewerConnection(String server, int port) {
        client = new Client(server, port);

        String message = client.getNewMove();
        while (message.contains("F")) {
            board.addCode(message);
            message = client.getNewMove();
        }
        board.addCode(message);
    }



    //volgende zet
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

    //gaat één zet terug
    public void back() {
        int turn = board.getTurn();

        ArrayList<Coordinate> p = board.getFieldsInOrder();
        Coordinate lastPlaced = p.get(p.size()-1);
        int row = lastPlaced.getRow();
        int column = lastPlaced.getColumn();
        PieceType type = board.getPieces()[row][column].getType();
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


