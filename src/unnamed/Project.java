package unnamed;

import main.Main;

import java.util.HashMap;

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

}
