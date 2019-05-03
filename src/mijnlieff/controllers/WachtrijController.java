package mijnlieff.controllers;

import javafx.beans.Observable;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import mijnlieff.pieces.Color;
import mijnlieff.tasks.WaitForAnswerTask;
import java.util.ArrayList;

//controllerklasse voor het wachtrijscherm
public class WachtrijController extends MijnlieffController {

    private ArrayList<String> opponents;
    public ListView<String> listView;
    public Label nothingSelected;
    public Label noPlayer;
    public Label fatalError;
    public Label waiting;
    public Button challenge;
    public Button refresh;
    public Button queue;
    private WaitForAnswerTask waitTask;

    // plaatst alle actieve spelers in de wachtrij
    public void refreshList() {
        listView.getItems().clear();
        opponents = client.getOpponents();
        if (opponents.contains("+") && opponents.size() == 1) {
            noPlayer.setVisible(true);
            nothingSelected.setVisible(false);
        } else {
            for (String opponent : opponents) {
                if (!opponent.equals("+")) {
                    listView.getItems().add(opponent.substring(2));
                }
            }
            noPlayer.setVisible(false);
        }
        nothingSelected.setVisible(false);
        fatalError.setVisible(false);
        waiting.setVisible(false);
    }

    //vraagt aan de client om de gekozen speler uit te dagen
    public void challengePlayer() {
        String selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            nothingSelected.setVisible(false);
            String answer = client.challengePlayer(selected);
            handleAnswer(answer);
        } else {
            nothingSelected.setVisible(true);

        }
        noPlayer.setVisible(false);
        fatalError.setVisible(false);
        waiting.setVisible(false);
    }

    public void handleAnswer(String serverAnswer) {
        if (serverAnswer.equals("-")) {
            fatalError.setText("Deze speler is al bezet");
        } else if (serverAnswer.charAt(2) == 'F') {

            waitTask = new WaitForAnswerTask(client);
            waitTask.stateProperty().addListener(this::boardStateChanged);
            new Thread(waitTask).start();
        } else if (serverAnswer.charAt(2) == 'T'){

            Scene next = changeScene("KeuzeScherm.fxml", 608, 837);
            Stage stage = (Stage) fatalError.getScene().getWindow();
            stage.setScene(next);

        }
    }



    public void enterQueue() {

        client.enterQueue();
        waitTask = new WaitForAnswerTask(client);
        waitTask.stateProperty().addListener(this::challengeStateChanged);
        new Thread(waitTask).start();
        waiting.setVisible(true);
        challenge.setDisable(true);
        refresh.setDisable(true);
        nothingSelected.setVisible(false);
        noPlayer.setVisible(false);
        fatalError.setVisible(false);

    }

    //wordt uitgevoerd als de tegenstander een bord heeft gekozen
    public void boardStateChanged(Observable o) {
        if (waitTask.getState() == Worker.State.SUCCEEDED) {
            Stage stage = (Stage) fatalError.getScene().getWindow();
            client.setColor(Color.WHITE);
            MijnlieffGameController mijnlieffGameController = new MijnlieffGameController(waitTask.getValue(), stage, client);

        } else if (waitTask.getState() == Worker.State.FAILED) {
            fatalError.setText("Er ging helaas iets mis...");
        }
    }

    //wordt uitgevoerd als je wordt uitgedaagd
    public void challengeStateChanged(Observable o) {
        if (waitTask.getState() == Worker.State.SUCCEEDED) {
            String serverAnswer = waitTask.getValue();

            handleAnswer(serverAnswer);

        } else if (waitTask.getState() == Worker.State.FAILED) {
            fatalError.setText("Er ging helaas iets mis");
        }
    }
}
