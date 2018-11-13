package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Preview{
    private FXMLLoader loader;
    private BorderPane root;


    public Preview() throws IOException {
        loader = new FXMLLoader();
        root = loader.load(new FileInputStream("fxmls/Preview.fxml"));
    }

    public BorderPane getRoot() {
        return root;
    }
}
