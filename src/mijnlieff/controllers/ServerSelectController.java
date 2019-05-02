package mijnlieff.controllers;


import javafx.beans.Observable;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mijnlieff.tasks.ServerConnectionTask;

//controller voor het beginscherm waar het serveradres gekozen wordt
public class ServerSelectController extends MijnlieffController {

    public TextField portField;
    public TextField serverField;
    public Label errorLabel;
    public ServerConnectionTask conTask;



    public void gameConnection() {
        String server = serverField.getText();
        String poort = portField.getText();


        if (!server.equals("") && !poort.equals("")) {

            errorLabel.setText("Aan het verbinden...");
            conTask = new ServerConnectionTask(server, Integer.parseInt(poort));
            conTask.stateProperty().addListener(this::connectionChange);
            new Thread(conTask).start();

        } else {
            errorLabel.setText("Gelieve beide velden in te vullen");
        }
    }

    //wordt uitgevoerd als de verbinding met de server gemaakt is of als ze niet werkt
    public void connectionChange(Observable o) {
        if (conTask.getState() == Worker.State.SUCCEEDED) {
            client = conTask.getValue();
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
