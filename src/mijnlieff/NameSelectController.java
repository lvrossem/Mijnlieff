package mijnlieff;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mijnlieff.server.Client;

public class NameSelectController extends MijnlieffController {


    public Label errorLabel;
    public TextField nameField;
    public Button nameConfirm;




    public void processName() {
        String name = nameField.getText();
        if (name.isEmpty()) {
            errorLabel.setText("Gelieve eerst een naam in te vullen");
        } else {

            String answer = client.checkName(name);
            if (answer.equals("+")) {
                System.out.println("sgoed jonge");
                Scene next = changeScene("Wachtrij.fxml", 600, 600);

                Stage primaryStage = (Stage) errorLabel.getScene().getWindow();

                primaryStage.setScene(next);
            } else {
                errorLabel.setText("Deze naam is al in gebruik");
            }
            System.out.println(answer);
        }
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
