package unnamed;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Main;

import java.io.File;
import java.util.HashMap;

import static main.Main.ew;

public class Project {
    private String path;
    private String name;

    private HashMap<String, Location> locations;

    public Project(String path, String name) {
        this.path = path;
        this.name = name;
        locations = new HashMap<>();

        locations.put(
                "World",
                new Location(
                        "World",
                        Main.getMainController().getLocationChoiceBox()
                )
        );
    }

    public Project() {
        this("", "unnamed");
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Location> getLocations() {
        return locations;
    }

    public void save(boolean saveAs) {
        String savePath;
        if (path.equals("")||saveAs){
            FileChooser fileChooser = new FileChooser();
            Stage stage = new Stage();
            fileChooser.setInitialFileName(name);
            File saveMetaData = fileChooser.showSaveDialog(stage);
            if (saveMetaData == null) return;
            savePath = saveMetaData.getAbsolutePath();
            name = saveMetaData.getName();
            if (path.equals("")){
                path = savePath;
            }
            if (!new File(savePath+"/images").mkdirs()) {
                ew.throwError("save error");
            }
        }else savePath = path;


    }
}
