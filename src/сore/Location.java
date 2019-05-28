package сore;

public class Location {
    private String name;

    public Location(String name) {
        this.name = name;
    }

    static void createLocation(String name){
        Main.locationList.add(new Location(name));
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
