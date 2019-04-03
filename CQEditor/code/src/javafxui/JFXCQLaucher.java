package javafxui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JFXCQLaucher extends Application {

    public static Stage JFXCQLaucherStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException  {
        JFXCQLaucher.JFXCQLaucherStage = primaryStage;

        FXMLLoader loader=new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        loader.setController(new MainScreenController());
        Parent root =  loader.load();

        primaryStage.setTitle("CQAuthor");
        primaryStage.setScene(new Scene(root, CONFIG.WINDOW_WIDTH, CONFIG.WINDOW_HEIGHT));
        primaryStage.show();
    }
}
