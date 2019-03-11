package unnamed;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static main.Main.ew;

public class AddLocationMenuController {

    @FXML
    public TextField nameTextField;
    @FXML
    public TextField imagePathTextField;

    @FXML
    public void initialize(){

    }

    @FXML
    void choseImage(){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        imagePathTextField.setText(fileChooser.showOpenDialog(stage).getAbsolutePath());
    }

    @FXML
    void okPressed() throws FileNotFoundException {
        String name = nameTextField.getText();

        //Verifying
        if (name.equals("")){
            ew.throwError("Enter name, please.");
            return;
        }

        Image image = new Image(new FileInputStream(imagePathTextField.getText()));

        Main.getLocations().put(
                name,
                new Location(name, Main.getMainController().getLocationChoiceBox(), image)
        );
    }

    @FXML
    void cancelPressed(){
        // TODO: 11.03.2019 Make cancel
    }

    public AddLocationMenuController() {
    }
}
