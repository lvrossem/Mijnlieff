package mijnlieff.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import mijnlieff.pieces.Color;

import java.util.HashMap;

//controller van het scherm waarmee je een bordconfiguratie kan kiezen
public class BordKeuzeController extends MijnlieffController {

    public ListView<String> choices;
    public Button confirm;
    public Label errorLabel;

    //linkt alle opties van het bord aan hun stringvoorstelling
    private static HashMap<Integer, String> bordPerIndex = new HashMap<>();
    static {
        bordPerIndex.put(0, "X 0 0 2 2 4 4 6 6");
        bordPerIndex.put(1, "X 1 0 0 2 3 0 2 2");
        bordPerIndex.put(2, "X 0 2 2 4 2 0 4 2");
        bordPerIndex.put(3, "X 0 0 0 4 2 2 4 2");
    }

    public void initialize() {
        choices.getItems().add(
                "   0 1 2 3 4 5 6 7\n" +
                "0 X X            \n" +
                "1 X X             \n" +
                "2      X X         \n" +
                "3      X X         \n" +
                "4           X X     \n" +
                "5           X X     \n" +
                "6                X X\n" +
                "7                X X");

        choices.getItems().add(
                "   0 1 2 3 4\n" +
                "0       X X\n" +
                "1 X X X X\n" +
                "2 X X X X\n" +
                "3 X X X X\n" +
                "4 X X        ");

        choices.getItems().add(
                "   0 1 2 3 4 5 6 7\n" +
                "0       X X       \n" +
                "1       X X       \n" +
                "2  X X      X X   \n" +
                "3  X X      X X   \n" +
                "4       X X       \n" +
                "5       X X         ");

        choices.getItems().add(
                "   0 1 2 3 4 5 6 7\n" +
                "0  X X      X X   \n" +
                "1  X X      X X   \n" +
                "2       X X       \n" +
                "3       X X       \n" +
                "4       X X     \n" +
                "5       X X      ");

    }

    //maakt het spel aan als er een keuze gemaakt is
    public void pickShape() {
        client.setColor(Color.BLACK);
        if (choices.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setVisible(true);
        } else {

            errorLabel.setVisible(false);
            String configuration = bordPerIndex.get(choices.getSelectionModel().getSelectedIndex());
            Stage stage = (Stage) errorLabel.getScene().getWindow();

            MijnlieffGameController mijnlieffGameController = new MijnlieffGameController(configuration, stage, client);

            client.sendBoard(configuration);

        }
    }
}
