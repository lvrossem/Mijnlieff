package mijnlieff.controllers;


import javafx.beans.Observable;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mijnlieff.server.Client;
import mijnlieff.tasks.ServerConnectionTask;

//controller voor het beginscherm waar het serveradres gekozen wordt
public class ServerSelectController extends MijnlieffController {

    public TextField portField;
    public TextField serverField;
    public Label errorLabel;
    public ServerConnectionTask conTask;

    public void initialize() {
        client = new Client();
    }

    public void gameConnection() {
        errorLabel.setText(null);
        String server = serverField.getText();
        String port = portField.getText();


        if (!server.equals("") && !port.equals("")) {

            boolean isNumber = true;
            try {
                int portNumber = Integer.parseInt(port);
            } catch (NumberFormatException ex) {
                isNumber = false;
            }

            if (isNumber) {
                conTask = new ServerConnectionTask(server, Integer.parseInt(port));
                conTask.stateProperty().addListener(this::connectionChange);
                new Thread(conTask).start();
                errorLabel.setText("Aan het verbinden...");
            } else {
                errorLabel.setText("Ongeldig poortnummer");
            }

        } else {
            errorLabel.setText("Gelieve beide velden in te vullen");
        }
    }

    //wordt uitgevoerd als de verbinding met de server gemaakt is of als ze niet werkt
    public void connectionChange(Observable o) {
        if (conTask.getState() == Worker.State.SUCCEEDED) {
            client.setSocket(conTask.getValue());
            errorLabel.setText(null);
            Scene next = changeScene("NameSelect.fxml", 201, 439);

            //verkrijgt de huidige window waarin de scene vervangen moet worden
            Stage primaryStage = (Stage) errorLabel.getScene().getWindow();

            primaryStage.setScene(next);
        } else if (conTask.getState() == Worker.State.FAILED) {
            errorLabel.setText("Ongeldige server");
        }
    }


}
