package customs;

import javafx.fxml.FXMLLoader;

import java.io.FileInputStream;
import java.io.IOException;

public class Option extends Output{
    public Option() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load(new FileInputStream("fxmls/Option.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
