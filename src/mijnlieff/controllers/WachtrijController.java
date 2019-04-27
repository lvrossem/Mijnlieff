package mijnlieff.controllers;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import mijnlieff.controllers.MijnlieffController;

import java.util.ArrayList;

public class WachtrijController extends MijnlieffController {

    private ArrayList<String> opponents;
    public ListView<String> listView;
    public Label nothingSelected;
    public Label noPlayer;

    // plaatst alle actieve spelers in de wachtrij
    public void refreshList() {
        listView.getItems().clear();
        opponents = client.getOpponents();
        if (opponents.contains("+") && opponents.size() == 1) {
            noPlayer.setVisible(true);
        } else {
            for (String opponent : opponents) {
                if (!opponent.equals("+")) {
                    listView.getItems().add(opponent.substring(2));
                }
            }
            noPlayer.setVisible(false);
        }
    }

    //vraagt aan de client om de gekozen speler uit te dagen
    public void challengePlayer() {
        String selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            nothingSelected.setVisible(false);
            String answer = client.challengePlayer(selected);
        } else {
            nothingSelected.setVisible(true);
        }
    }


    public void enterQueue() {
        client.enterQueue();
    }


}
