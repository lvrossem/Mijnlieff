package mijnlieff;

import javafx.scene.layout.GridPane;
import mijnlieff.models.MijnlieffBoard;
import mijnlieff.views.Field;

import java.util.ArrayList;

public class GameController {

    private String repr = "X 0 0 2 2 4 4 6 6";

    public GridPane gridPane;
    private MijnlieffBoard board;

    public void initialize() {
        gridPane = new GridPane();
        String indices = repr.substring(2);
        for (int i = 0; i < 4; i++) {
            int row = Character.getNumericValue(indices.charAt(4*i));
            int column = Character.getNumericValue(indices.charAt(4*i+2));
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    Field field = new Field();
                    field.setModel(board);
                    gridPane.add(field, row + j, column + k);
                    field.setRow();
                }
            }


        }




    }


}
