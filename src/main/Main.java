package main;

import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import window.ErrorWindow;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main extends Application {
    private static FXMLLoader loader;
    public static ErrorWindow ew;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ew = new ErrorWindow() {
            @Override
            public void throwError(String text) {
                super.throwError(text);
            }
        };
        loader = new FXMLLoader();
        BorderPane root = loader.load(new FileInputStream("fxmls/Main.fxml"));
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static FXMLLoader getLoader() {
        return loader;
    }
}
