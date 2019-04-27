package mijnlieff;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mijnlieff.controllers.MijnlieffBoardController;
import mijnlieff.models.MijnlieffBoard;
import mijnlieff.models.SidePieces;
import mijnlieff.pieces.Color;
import mijnlieff.views.Field;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.List;

public class Mijnlieff extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        List<String> args = getParameters().getRaw();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Mijnlieff");
        primaryStage.getIcons().add(new Image("mijnlieff/img/logo3.png"));
        Parent root;
        Scene scene;

        if (args.size() == 0) {
            root = FXMLLoader.load(getClass().getResource("ServerSelect.fxml"));
            scene = new Scene(root, 600.0, 350.0);
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Mijnlieff.fxml"));
            root = loader.load();
            scene = new Scene(root, 936.0, 712.0);
            MijnlieffBoardController controller = loader.getController();
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


        /*
        MijnlieffBoard board = new MijnlieffBoard();

        String repr = "X 0 2 2 4 2 0 4 2";
        BorderPane borderPane = new BorderPane();


        SidePieces whiteSide = new SidePieces();
        SidePieces blackside = new SidePieces();
        whiteSide.setColor(Color.WHITE);
        blackside.setColor(Color.BLACK);
        whiteSide.fireInvalidationEvent();
        blackside.fireInvalidationEvent();

        String indices = repr.substring(2);

        for (int i = 0; i < 4; i++) {
            int row = Character.getNumericValue(indices.charAt(4*i));
            int column = Character.getNumericValue(indices.charAt(4*i+2));
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    Field field = new Field();
                    field.setFitHeight(80);
                    field.setFitWidth(80);
                    field.setModel(board);
                    board.add(field, column + j, row + k);
                    field.setRow();
                }
            }
        }

        board.fireInvalidationEvent();
        board.setAlignment(Pos.CENTER);

        borderPane.setCenter(board);
        borderPane.setLeft(whiteSide);
        borderPane.setRight(blackside);


        Scene test = new Scene(borderPane, 900, 700);
        primaryStage.setScene(test);
        primaryStage.show();
        **/








    }




    public static void main(String[] args) {

        launch(args);

    }
}
