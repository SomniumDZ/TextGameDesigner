package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import unnamed.Location;
import window.ErrorWindow;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class Main extends Application {
    public static ErrorWindow ew;

    private static HashMap<String, Location> locations;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        //Error window init
        ew = new ErrorWindow() {
            @Override
            public void throwError(String text) {
                super.throwError(text);
            }
        };

        locations = new HashMap<>();

        //Files init
        FileInputStream mainFXMLFile = new FileInputStream("fxmls/Main.fxml");
        FileInputStream nodeFXMLFile = new FileInputStream("fxmls/NodeView.fxml");

        FXMLLoader mainFXMLLoader = new FXMLLoader();
        BorderPane root = new BorderPane();
        mainFXMLLoader.setRoot(root);
        mainFXMLLoader.load(mainFXMLFile);
        Scene scene = new Scene(root, 600, 400);

        locations.put(
                "World",
                new Location("World", ((MainController) mainFXMLLoader.getController()).getLocationChoiceBox())
        );

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static HashMap<String, Location> getLocations() {
        return locations;
    }
}
