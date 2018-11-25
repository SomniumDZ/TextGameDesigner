package controllers.nodes.events;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.UUID;

public class OptionEditor extends GridPane{
    @FXML
    private TextField message;
    @FXML
    private Button delete;
    @FXML
    private Button add;

    private String id;

    private String inMessage;
    private EventEditor editor;

    public OptionEditor(String message, EventEditor editor, String id) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/OptionEditor.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
        inMessage = message;
        this.message.setText(inMessage);
        this.editor = editor;
        this.id = id;
    }

    @FXML
    public void initialize(){
        delete.setOnAction(event -> {
            if (editor.getOutputs().size()>1)
            editor.removeOption(this);
        });
        add.setOnAction(event -> {
            try {
                editor.addOption(new OptionEditor("option", editor, UUID.randomUUID().toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public String getText() {
        return message.getText();
    }

    public String getOptionId() {
        return id;
    }


}
