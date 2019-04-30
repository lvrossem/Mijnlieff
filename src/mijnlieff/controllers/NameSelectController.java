package mijnlieff.controllers;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mijnlieff.controllers.MijnlieffController;
import mijnlieff.server.Client;

//companionclass van het naamselectiescherm
public class NameSelectController extends MijnlieffController {


    public Label errorLabel;
    public TextField nameField;
    public Button nameConfirm;



    //registreert de naam bij de server
    public void processName() {
        String name = nameField.getText();
        if (name.isEmpty()) {
            errorLabel.setText("Gelieve eerst een naam in te vullen");
        } else {

            String answer = client.checkName(name);
            if (answer.equals("+")) {
                System.out.println("sgoed jonge");
                Scene next = changeScene("Wachtrij.fxml", 400, 600);

                Stage primaryStage = (Stage) errorLabel.getScene().getWindow();

                primaryStage.setScene(next);
            } else {
                errorLabel.setText("Deze naam is al in gebruik");
            }

        }
    }


}
