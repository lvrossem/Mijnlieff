package mijnlieff.controllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import mijnlieff.server.Client;

import java.io.IOException;

public abstract class MijnlieffController {

    protected Client client;

    public void quit() {
        Platform.exit();

    }



    public void setClient(Client client) {
        this.client = client;
    }

    public Scene changeScene(String fxml, int height, int width) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mijnlieff/" + fxml));

        Scene scene;
        try {
            Parent root = loader.load();
            ((MijnlieffController) loader.getController()).setClient(client);
            System.out.println("doorgegeven");
            scene = new Scene(root, width, height);
            return scene;

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
