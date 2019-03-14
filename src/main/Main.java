package main;

import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import unnamed.Project;
import window.ErrorWindow;

import java.io.FileInputStream;
import java.io.IOException;

public class Main extends Application {
    public static ErrorWindow ew;

    private static FXMLLoader mainFXMLLoader;

    private static Project currentProject;

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

        //Files init
        FileInputStream mainFXMLFile = new FileInputStream("fxmls/Main.fxml");

        mainFXMLLoader = new FXMLLoader();
        BorderPane root = new BorderPane();
        mainFXMLLoader.setRoot(root);
        System.out.println(mainFXMLFile.toString());
        mainFXMLLoader.load(mainFXMLFile);
        mainFXMLFile.close();
        Scene scene = new Scene(root, 600, 400);

        currentProject = new Project();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Project getCurrentProject() {
        return currentProject;
    }

    public static void setCurrentProject(Project currentProject) {
        Main.currentProject = currentProject;
    }

    private static FXMLLoader getMainFXMLLoader() {
        return mainFXMLLoader;
    }

    public static MainController getMainController(){
        return getMainFXMLLoader().getController();
    }
}
