package mijnlieff;

import javafx.application.Application;
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




        Parent root = FXMLLoader.load(getClass().getResource("Mijnlieff.fxml"));


        primaryStage.setTitle("Mijnlieff");
        primaryStage.setResizable(false);
        Scene scene = new Scene(root, 936.0, 712.0);

        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("mijnlieff/img/logo3.png"));

        primaryStage.show();

        //controller.end();
        WritableImage image = scene.snapshot(null);
        File out = new File("Snapshot_complete");
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", out);

        
    }




    public static void main(String[] args) {

        launch(args);

    }
}
