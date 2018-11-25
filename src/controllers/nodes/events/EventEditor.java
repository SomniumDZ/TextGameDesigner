package controllers.nodes.events;

import controllers.nodes.Output;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class EventEditor extends BorderPane {

    @FXML
    private VBox optionsBox;
    @FXML
    private TextArea messageField;
    @FXML
    private ScrollPane scrollPane;

    private Event item;


    public EventEditor(Event eventItem) throws IOException {
        item = eventItem;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/EventEditor.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    @FXML
    public void initialize(){
        messageField.setText(item.getInput());
        item.getOutputs().forEach((id, output) ->{
            OptionEditor cur = null;
            try {
                cur = new OptionEditor(output.getMessage(), this, id);
            } catch (IOException e) {
                e.printStackTrace();
            }
            optionsBox.getChildren().add(cur);
        });
        optionsBox.prefWidthProperty().bind(scrollPane.widthProperty().add(-5));
        show();
    }

    private void show() {
        Stage stage = new Stage();
        Scene scene = new Scene(this);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Event Editor");
        stage.showAndWait();
    }

    public void removeOption(OptionEditor optionEditor) {
        optionsBox.getChildren().remove(optionEditor);
    }

    public void addOption(OptionEditor option){
        optionsBox.getChildren().add(option);
    }

    public String getEventMessage(){
        return messageField.getText();
    }

    public HashMap<String, Output> getOutputs(){
        HashMap<String, Output> returned = new HashMap<>();
        optionsBox.getChildren().forEach(node -> {
            returned.put(((OptionEditor)node).getOptionId(), new Output(((OptionEditor)node).getText()));
        });
        return returned;
    }
}
