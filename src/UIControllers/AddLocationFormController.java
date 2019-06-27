package UIControllers;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import сore.Location;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AddLocationFormController {
    public TextField nameField;
    public TextField imageField;
    public Button browseImageButton;
    public ImageView previewImageView;
    public Button okButton;
    public Button cancelButton;
    private Image image;

    public AddLocationFormController() {
    }

    public void initialize(){
        FileChooser fileChooser = new FileChooser();
        browseImageButton.setOnAction(event -> {
            File imageFile = fileChooser.showOpenDialog(new Stage());
            imageField.setText(imageFile.getAbsolutePath());
        });
        imageField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                image = new Image(new FileInputStream(newValue));
            } catch (FileNotFoundException ignored) {

            }
            previewImageView.setImage(image);
        });
        okButton.setDefaultButton(true);
        cancelButton.setCancelButton(true);
        okButton.setOnAction(event -> {
            Location.createLocation(nameField.getText(), image);
            BaseController.ADD_LOCATION_STAGE.close();
        });
        cancelButton.setOnAction(event -> BaseController.ADD_LOCATION_STAGE.close());
    }
}
