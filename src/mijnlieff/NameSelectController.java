package mijnlieff;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mijnlieff.server.Client;

public class NameSelectController {

    private Client client;
    public Label errorLabel;
    public TextField nameField;
    public Button nameConfirm;


    public void processName() {
        String name = nameField.getText();
        if (name.equals("")) {
            errorLabel.setText("Gelieve eerst een naam in te voeren");
        } else {

            String answer = client.checkName(name);
            if (answer.equals("+")) {
                System.out.println("sgoed jonge");
                errorLabel.setText(null);
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
