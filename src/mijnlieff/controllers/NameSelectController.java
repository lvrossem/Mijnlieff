package mijnlieff.controllers;

import javafx.beans.Observable;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mijnlieff.tasks.WaitForAnswerTask;

//companionclass van het naamselectiescherm
public class NameSelectController extends MijnlieffController {


    public Label errorLabel;
    public TextField nameField;
    public Button nameConfirm;
    private WaitForAnswerTask waitTask;



    //registreert de naam bij de server
    public void processName() {
        String name = nameField.getText();
        if (name.isEmpty()) {
            errorLabel.setText("Gelieve eerst een naam in te vullen");

        } else if (name.contains(" ")) {
            errorLabel.setText("De naam mag geen spaties bevatten");
        } else {

            client.checkName(name);
            waitTask = new WaitForAnswerTask(client);
            waitTask.stateProperty().addListener(this::nameStateChanged);
            new Thread(waitTask).start();
            errorLabel.setText("Aan het wachten op een antwoord...");
        }
    }

    //wordt uitgevoerd als de server zegt of de naam wel of niet beschikbaar is
    public void nameStateChanged(Observable o) {
        if (waitTask.getState() == Worker.State.SUCCEEDED) {

            if (waitTask.getValue().equals("+")) {

                Scene next = changeScene("Wachtrij.fxml", 400, 600);

                Stage primaryStage = (Stage) errorLabel.getScene().getWindow();

                primaryStage.setScene(next);
            } else {
                errorLabel.setText("Deze naam is al in gebruik");
            }
        }
    }


}
