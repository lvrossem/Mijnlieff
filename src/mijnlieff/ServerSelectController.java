package mijnlieff;


import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mijnlieff.server.Client;

public class ServerSelectController extends MijnlieffController {

    public TextField portField;
    public TextField serverField;
    public Label errorLabel;


    public void gameConnection() {
        String server = serverField.getText();
        String poort = portField.getText();


        if (!server.equals("") && !poort.equals("")) {


            client = new Client(server, Integer.parseInt(poort));
            if (client.equals(null)) {
                System.out.println("er is geen client");
            }
            System.out.println("verbinding is gelukt");
            Scene next = changeScene("NameSelect.fxml", 253, 586);

            Stage primaryStage = (Stage) errorLabel.getScene().getWindow();

            primaryStage.setScene(next);

        } else {
            errorLabel.setVisible(true);
        }


    }


}
