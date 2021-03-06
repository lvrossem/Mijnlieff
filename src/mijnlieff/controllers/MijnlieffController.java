package mijnlieff.controllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import mijnlieff.server.Client;
import mijnlieff.tasks.WaitForAnswerTask;

import java.io.IOException;

// Abstracte superklasse van de controllers waarin de gemeenschappelijke methoden en velden staan
public abstract class MijnlieffController {

    protected Client client;
    protected WaitForAnswerTask waitTask;

    public void quit() {
        client.closeConnection();
        Platform.exit();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    // Bouwt een nieuwe scene aan de hand van de padnaam van een fxml-bestand en de dimensies van de window
    public Scene changeScene(String fxml, int height, int width) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mijnlieff/fxml/" + fxml));
	Scene scene;

        try {
            Parent root = loader.load();
            ((MijnlieffController) loader.getController()).setClient(client);
            scene = new Scene(root, width, height);
            return scene;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
