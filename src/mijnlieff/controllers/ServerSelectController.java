package mijnlieff.controllers;


import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mijnlieff.controllers.MijnlieffController;
import mijnlieff.server.Client;

public class ServerSelectController extends MijnlieffController {

    public TextField portField;
    public TextField serverField;
    public Label errorLabel;



    public void gameConnection() {
        String server = serverField.getText();
        String poort = portField.getText();


        if (!server.equals("") && !poort.equals("")) {

            errorLabel.setVisible(false);
            client = new Client(server, Integer.parseInt(poort));


            Scene next = changeScene("NameSelect.fxml", 201, 439);

            //verkrijgt de huidige window waarin de scene vervangen moet worden
            Stage primaryStage = (Stage) errorLabel.getScene().getWindow();

            primaryStage.setScene(next);

        } else {
            errorLabel.setVisible(true);
        }


    }


}
