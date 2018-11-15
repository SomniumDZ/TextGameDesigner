package controllers.events;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class FOptionsEvent extends Event<GridPane, FOptionsEventVisualController> {

    public FOptionsEvent(double x, double y) throws IOException {
        super(x, y, "fxmls/4OptionsEventVisual.fxml", "fxmls/4OptionsEventEditor.fxml", new Event[4]);
    }

    @Override
    void onEditorCloses() {
        Stage atss = new Stage();
        atss.initModality(Modality.APPLICATION_MODAL);
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        HBox question = new HBox(10);
        question.setAlignment(Pos.CENTER);
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        question.getChildren().add(new Label("Save changes?"));
        Button yes = new Button("Yes");
        Button no = new Button("No");
        buttons.getChildren().addAll(yes, no);
        root.getChildren().addAll(question, buttons);

        Scene scene = new Scene(root);
        atss.setScene(scene);
        yes.setOnAction(event -> {
            getVisualController().option1.setText(getController().option1.getText());
            getVisualController().option2.setText(getController().option2.getText());
            getVisualController().option3.setText(getController().option3.getText());
            getVisualController().option4.setText(getController().option4.getText());
            atss.close();
        });
        no.setOnAction(event -> atss.close());
        atss.showAndWait();
    }
}
