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

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Mijnlieff extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        FXMLLoader loader = new FXMLLoader(getClass().getResource("Mijnlieff.fxml"));

        Parent root = loader.load();

        MijnlieffController controller = loader.getController();

        primaryStage.setTitle("Mijnlieff");
        primaryStage.setResizable(false);
        Scene scene = new Scene(root, 936.0, 712.0);

        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("mijnlieff/img/logo3.png"));
        controller.makeConnection(getParameters().getRaw().get(0), Integer.parseInt(getParameters().getRaw().get(1)));


        if (getParameters().getRaw().size() == 3) {
            controller.end();
            WritableImage image = scene.snapshot(null);
            File out = new File(getParameters().getRaw().get(2));
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", out);
            Platform.exit();
        } else {
            primaryStage.show();
        }
    }




    public static void main(String[] args) {

        launch(args);

    }
}
