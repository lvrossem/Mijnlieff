package mijnlieff;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import mijnlieff.server.Client;

import java.io.IOException;

public class ServerSelectController {

    public TextField portField;
    public TextField serverField;
    public Label errorLabel;

    private Client client;

    public void gameConnection() {
        String server = serverField.getText();
        String poort = portField.getText();


        if (!server.equals("") && !poort.equals("")) {

            try {
                client = new Client(server, Integer.parseInt(poort));
                System.out.println("verbinding is gelukt");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("NameSelect.fxml"));

                Parent root = loader.load();
                ((NameSelectController) loader.getController()).setClient(client);
                Stage primaryStage = (Stage) errorLabel.getScene().getWindow();
                Scene scene = new Scene(root, 600.0, 350.0);

                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException ex) {
                System.out.println("werkt ni jonge kut");
            }

        } else {
            errorLabel.setVisible(true);
        }


    }


}
