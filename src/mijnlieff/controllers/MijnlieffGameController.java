package mijnlieff.controllers;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mijnlieff.models.MijnlieffBoard;
import mijnlieff.models.SidePieces;
import mijnlieff.pieces.Color;
import mijnlieff.pieces.Piece;
import mijnlieff.views.Field;




//controllerklasse voor een spelsessie met 2 spelers (dus niet voor de viewer)
public class MijnlieffGameController extends MijnlieffController {

    public BorderPane borderPane;
    public MijnlieffBoard board;
    public SidePieces whiteSide;
    public SidePieces blackSide;
    private Piece selected;

    //stelt het bord op adhv de stringvorm van de bordconfiguratie
    public MijnlieffGameController(String configuration, Stage stage) {
        MijnlieffBoard board = new MijnlieffBoard();


        BorderPane borderPane = new BorderPane();


        SidePieces whiteSide = new SidePieces();
        SidePieces blackside = new SidePieces();
        whiteSide.setColor(Color.WHITE);
        blackside.setColor(Color.BLACK);
        whiteSide.setController(this);
        blackside.setController(this);
        whiteSide.fireInvalidationEvent();
        blackside.fireInvalidationEvent();

        String indices = configuration.substring(2);

        //maakt de speelvelden per blokjes van 4 aan
        for (int i = 0; i < 4; i++) {
            int row = Character.getNumericValue(indices.charAt(4*i));
            int column = Character.getNumericValue(indices.charAt(4*i+2));
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    Field field = new Field();
                    field.setFitHeight(80);
                    field.setFitWidth(80);
                    board.add(field, column + j, row + k);
                    field.setRow(row+k);
                    field.setColumn(column+j);
                    field.setModel(board);
                }
            }
        }


        board.fireInvalidationEvent();
        board.setAlignment(Pos.CENTER);

        borderPane.setCenter(board);
        borderPane.setLeft(whiteSide);
        borderPane.setRight(blackside);


        Scene game = new Scene(borderPane, 900, 700);
        stage.setScene(game);
        stage.show();




    }

    public void setSelected(Piece piece) {
        selected = piece;
        System.out.println(piece.getType().getUrl());
        System.out.println(piece.getColor().getColorString());
    }

    public Piece getSelected() {
        return selected;
    }


}
