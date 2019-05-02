package mijnlieff;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import mijnlieff.controllers.MijnlieffGameController;
import mijnlieff.controllers.MijnlieffViewerController;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.List;

public class Mijnlieff extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {


        List<String> args = getParameters().getRaw();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Mijnlieff");
        primaryStage.getIcons().add(new Image("mijnlieff/img/logo3.png"));
        Parent root;
        Scene scene;

        //kiest de juiste manier om op te starten adhv het aantal argumenten

        if (args.size() == 0) {
            root = FXMLLoader.load(getClass().getResource("fxml/ServerSelect.fxml"));
            scene = new Scene(root, 600.0, 350.0);
            primaryStage.setScene(scene);
            primaryStage.show();

        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Mijnlieff.fxml"));
            root = loader.load();
            scene = new Scene(root, 936.0, 712.0);
            MijnlieffViewerController controller = loader.getController();
            controller.viewerConnection(args.get(0), Integer.parseInt(args.get(1)));
            primaryStage.setScene(scene);
            if (args.size() == 3) {
                controller.end();
                WritableImage image = scene.snapshot(null);
                File out = new File(getParameters().getRaw().get(2));
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", out);
                Platform.exit();
            } else {
                primaryStage.show();
            }
        }








        //MijnlieffGameController controller = new MijnlieffGameController("X 0 0 2 1 4 3 6 8", primaryStage);


    }








    public static void main(String[] args) {

        launch(args);

    }
}
