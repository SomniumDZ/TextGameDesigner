package сore;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import сore.nodes.DraggablePane;

public class Main extends Application {
    public static ObservableList<Location> locationList;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        newProject();
        BorderPane base = FXMLLoader.load(getClass().getResource("/fxmls/base.fxml"));
        Scene scene = new Scene(base);

        primaryStage.setScene(scene);
        primaryStage.show();

        DraggablePane node =
    }

    public static void newProject(){
        locationList = FXCollections.observableArrayList();
        Location.createLocation("World");
    }
}
