package controllers;

import com.somnium.handler.XMLHandler;
import controllers.nodes.Node;
import controllers.nodes.Output;
import controllers.nodes.events.Event;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Preview;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import pawn.Location;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static main.Main.ew;

public class MainController {
    @FXML
    public MenuItem btnRunPreview;
    @FXML
    public Tab editorTab;
    @FXML
    public VBox npcList;
    @FXML
    public FlowPane locationsPane;
    @FXML
    public VBox sequenceEditorTools;
    @FXML
    public MenuItem save;
    @FXML
    public ChoiceBox<String> locationChoiceBox;
    @FXML
    public BorderPane eventsEditor;


    @FXML
    private MenuItem open;

    private Location chosenLocation;

    private Node selectedNode;

    private Node draggedNode;
    private Output draggedOut;
    private Node initialNode;

    private final FileChooser saveFileChooser = new FileChooser();


    public MainController() {
        Button addLocationButton = new Button("Add location");
    }



    public void initialize(){
        locationsPane.getChildren().addListener((ListChangeListener<? super javafx.scene.Node>) c -> {
            locationChoiceBox.getItems().clear();
            locationsPane.getChildren().forEach(location -> {
                locationChoiceBox.getItems().add(((Button)location).getText());
            });
        });

        locationChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            chosenLocation = (Location) locationsPane.getChildren().get(newValue.intValue());
            eventsEditor.setCenter(chosenLocation.getSequenceRoot());
        });

        chosenLocation = new Location("World", null);
        locationsPane.getChildren().add(chosenLocation);
        locationChoiceBox.setValue("World");


        btnRunPreview.setOnAction(event -> {
            new Preview(initialNode);
        });


        getChosenSequenceEditorRoot().setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.ANY);
            if (draggedNode !=null) {
                draggedNode.setTranslatePosition(event.getSceneX(), event.getSceneY(), true);
            }
            if (draggedOut!=null){
                draggedOut.setConnectorPosition(event.getSceneX(), event.getSceneY());
            }
            event.consume();
        });

        getChosenSequenceEditorRoot().setOnDragDropped(event -> {
            event.acceptTransferModes(TransferMode.ANY);
            draggedNode = null;
            if (draggedOut!=null) {
                draggedOut.reset();
                draggedOut = null;
            }
            Dragboard db = event.getDragboard();
            db.clear();
            event.setDropCompleted(true);
            event.consume();
        });



        initialNode = new Event(50,50);
        getChosenSequenceEditorRoot().getChildren().add(initialNode);
        chosenLocation.getNodes().put(initialNode.getId(), initialNode);

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Text Game Designer save file (*.tgd)", "*.tgd");
        saveFileChooser.getExtensionFilters().add(extFilter);

//        Saving
        save.setOnAction(event -> {
            Stage stage = new Stage();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document saveDocument = null;
            try {
                saveDocument = factory.newDocumentBuilder().newDocument();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
                ew.throwError("Save error");
            }
            assert saveDocument != null;
            Element root = saveDocument.createElement("Nodes");
            saveDocument.appendChild(root);
            Element events = saveDocument.createElement("events");
            root.appendChild(events);

            Document finalSaveDocument = saveDocument;
            chosenLocation.getNodes().forEach((nid, node) -> {
                switch (node.getClass().getSimpleName()){
                   case "Event":
                       Element eventElement = finalSaveDocument.createElement("event");
                       eventElement.setAttribute("id", nid);
                       eventElement.setAttribute("title", node.getTitle());
                       eventElement.setAttribute("message", node.getInput().getMessage());
                       eventElement.setAttribute("x", String.valueOf(node.getTranslateX()));
                       eventElement.setAttribute("y", String.valueOf(node.getTranslateY()));
                       node.getOutputs().forEach((oid, output) -> {
                           Element outputElement = finalSaveDocument.createElement("output");
                           outputElement.setAttribute("id", oid);
                           if (output.getContacted() != null) {
                               outputElement.setAttribute("contacted", output.getContacted().getId());
                           }
                           outputElement.setAttribute("message", output.getMessage());
                           eventElement.appendChild(outputElement);
                       });
                       events.appendChild(eventElement);
                       break;
               }
            });

            Transformer transformer = null;
            try {
                transformer = TransformerFactory.newInstance().newTransformer();
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
                ew.throwError("Save error");
            }
            assert transformer != null;
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            try {
                File file = saveFileChooser.showSaveDialog(stage);
                if (file == null) return;
                transformer.transform(new DOMSource(finalSaveDocument), new StreamResult(file));
            } catch (TransformerException e) {
                e.printStackTrace();
                ew.throwError("Save error");
            }
        });

//        Opening
        open.setOnAction(event -> {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            File opened = saveFileChooser.showOpenDialog(stage);

            if (opened == null) return;

            XMLHandler handler;

            SAXParserFactory factory = SAXParserFactory.newInstance();
            try {
                handler = new XMLHandler();
                SAXParser parser = factory.newSAXParser();
                parser.parse(opened, handler);
            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
                ew.throwError("Save parsing error");
                return;
            }

            parseSaveFile(handler.getRoot());
        });
    }


    private void parseSaveFile(com.somnium.handler.Element root) {
        HashMap<String, Node> reserve = new HashMap<>(getNodeMap());
        getNodeMap().clear();
        getChosenSequenceEditorRoot().getChildren().clear();

        root.getChildElements().forEach(element -> {
            switch (element.getName()){
                case "events":
                    element.getChildElements().forEach(event ->{
                        Event current = new Event(
                                Double.parseDouble(event.getAttribute("x")),
                                Double.parseDouble(event.getAttribute("y")),
                                event.getAttributes().get("id")
                        );
                        current.setTitle(event.getAttribute("title"));
                        event.getChildElements().forEach(output -> {
                            current.addOutput(new Output(
                                    output.getAttribute("id"),
                                    output.getAttribute("message")
                            ));
                        });
                    });
                    element.getChildElements().forEach(event ->{
                        event.getChildElements().forEach(output ->{
                            if (output.containsAttribute("contacted")){
                                Output out = getNodeMap().get(event.getAttribute("id")).getOutputs()
                                        .get(output.getAttribute("id"));
                                CubicCurve curve = out.spawnNewConnectionCurve(out.getContainer());
                                out.setCurve(curve);
                                curve.setMouseTransparent(true);
                                out.getConnector().setMouseTransparent(true);
                                getChosenSequenceEditorRoot().getChildren().addAll(curve, out.getConnector());
                                getNodeMap().get(output.getAttribute("contacted")).getInput().connect(
                                        event.getAttribute("id"),
                                        output.getAttribute("id")
                                );
                            }
                        });
                    });
                    break;
            }
        });

        getNodeMap().forEach((s, node) -> {
            getChosenSequenceEditorRoot().getChildren().add(node);
            node.toBack();
        });
    }

    public void setSelectedNode(Node node) {
        selectedNode = node;
        chosenLocation.getNodes().forEach((s, node1) -> node1.setEffect(null));
        sequenceEditorTools.getChildren().clear();
        if (selectedNode != null) {
            sequenceEditorTools.getChildren().clear();
            DropShadow effect = new DropShadow(15, Color.DARKORANGE);
            selectedNode.setEffect(effect);
            switch (selectedNode.getClass().getSimpleName()){
                case "Event":
                    showEventTools(node);
            }
        }

    }

    private void showEventTools(Node node){
        TitledPane root = new TitledPane("Event", node.getTools());
        sequenceEditorTools.getChildren().add(root);
    }

    public Pane getChosenSequenceEditorRoot() {
        return chosenLocation.getSequenceRoot();
    }

    public Node getDraggedNode() {
        return draggedNode;
    }

    public void setDraggedNode(Node draggedNode) {
        this.draggedNode = draggedNode;
    }

    public Output getDraggedOut() {
        return draggedOut;
    }

    public void setDraggedOut(Output draggedOut) {
        this.draggedOut = draggedOut;
    }

    public HashMap<String, Node> getNodeMap() {
        return chosenLocation.getNodes();
    }

    public Node getInitialNode() {
        return initialNode;
    }

    public void setInitialNode(Node node) {
        initialNode = node;
    }

    public Node getSelectedNode() {
        return selectedNode;
    }
}
