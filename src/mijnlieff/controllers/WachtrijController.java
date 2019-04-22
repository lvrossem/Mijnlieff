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

    public void refreshList() {
        opponents = client.getOpponents();
        if (opponents.contains("+") && opponents.size() == 1) {
            noPlayer.setVisible(true);
        } else {
            for (String opponent : opponents) {
                if (!opponent.equals("+")) {
                    listView.getItems().add(opponent);
                }
            }
            noPlayer.setVisible(false);
        }
    }

    public void challengePlayer() {
        String selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            nothingSelected.setVisible(false);
        } else {
            nothingSelected.setVisible(true);
        }
    }


}
