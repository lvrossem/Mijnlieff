package mijnlieff.controllers;

import javafx.beans.Observable;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mijnlieff.models.Coordinate;
import mijnlieff.models.MijnlieffBoard;
import mijnlieff.models.MoveData;
import mijnlieff.models.SidePieces;
import mijnlieff.pieces.Color;
import mijnlieff.pieces.Piece;
import mijnlieff.pieces.PieceType;
import mijnlieff.tasks.WaitForAnswerTask;
import mijnlieff.views.Field;

import java.util.HashMap;

//controllerklasse voor een spelsessie met 2 spelers (dus niet voor de viewer)
public class MijnlieffGameController extends MijnlieffController {

    public BorderPane borderPane;
    public MijnlieffBoard board;
    public SidePieces whiteSide;
    public SidePieces blackSide;
    public Label waitingError;
    public Button pointButton;
    private Piece selected;


    private static HashMap<PieceType, Character> charPerType = new HashMap<>();
    static {
        charPerType.put(PieceType.PULLER, 'o');
        charPerType.put(PieceType.PUSHER, '@');
        charPerType.put(PieceType.TOREN, '+');
        charPerType.put(PieceType.LOPER, 'X');
    }


    //stelt het bord op adhv de stringvorm van de bordconfiguratie
    public MijnlieffGameController(String configuration, Stage stage) {
        board = new MijnlieffBoard();


        borderPane = new BorderPane();

        waitingError = new Label();
        waitingError.setPrefHeight(17.6);
        waitingError.setPrefWidth(220);
        waitingError.setVisible(true);

        whiteSide = new SidePieces();
        blackSide = new SidePieces();
        whiteSide.setColor(Color.WHITE);
        blackSide.setColor(Color.BLACK);
        whiteSide.setController(this);
        blackSide.setController(this);
        board.setController(this);
        whiteSide.fireInvalidationEvent();
        blackSide.fireInvalidationEvent();

        String indices = configuration.substring(2);

        //maakt de speelvelden per blokjes van 4 aan
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                Field field = new Field();
                field.setFitHeight(67);
                field.setFitWidth(67);
                board.add(field, i, j);
            }
        }
        for (int i = 0; i < 4; i++) {
            int row = Character.getNumericValue(indices.charAt(4*i));
            int column = Character.getNumericValue(indices.charAt(4*i+2));
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    Field field = new Field();
                    field.setFitHeight(60);
                    field.setFitWidth(60);
                    board.add(field, column + j, row + k);
                    field.setRow(row+k);
                    field.setColumn(column+j);
                    field.setModel(board);
                }
            }
        }


        board.fireInvalidationEvent();
        board.setAlignment(Pos.CENTER);
        waitingError.setAlignment(Pos.CENTER);

        borderPane.setCenter(board);
        borderPane.setLeft(whiteSide);
        borderPane.setRight(blackSide);
        borderPane.setBottom(waitingError);


        Scene game = new Scene(borderPane, 900, 700);
        stage.setScene(game);
        stage.show();

        if (client.getColor() == Color.BLACK) {
            handleNextMove();
        } else {
            waitingError.setText("Het is jouw beurt");
        }

    }


    public void setSelected(Piece piece) {

        selected = piece;

    }


    public void setSelectedNull() {
        PieceType type = selected.getType();
        if (board.getTurn() % 2 == 0) {

            whiteSide.deletePieceImage(type);

        } else {
            blackSide.deletePieceImage(type);
        }

        selected = null;
        MoveData md = board.getLastPlaced();
        client.sendMove("X F " + md.getRow() + " " + md.getColumn() + " " + charPerType.get(board.getPieces()[md.getRow()][md.getColumn()]));

        handleNextMove();
    }

    public void handleNextMove() {
        waitingError.setText("Wachten op de zet van de tegenstander...");
        waitTask = new WaitForAnswerTask(client);
        waitTask.stateProperty().addListener(this::boardAnswerStateChanged);
        new Thread(waitTask).start();
    }

    public Piece getSelected() {
        return selected;
    }

    public void boardAnswerStateChanged(Observable o) {
        if (waitTask.getState() == Worker.State.SUCCEEDED) {
            board.addPiece(waitTask.getValue());
            waitingError.setText(null);
        } else if (waitTask.getState() == Worker.State.FAILED) {
            waitingError.setText("Er ging iets fout...");
        }
    }



}
