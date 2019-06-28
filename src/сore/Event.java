package сore;

import java.util.HashMap;

public class Event {
    HashMap<String, Event> caseMap;

    public Event() {
        this(new HashMap<>());
    }

    public Event(HashMap<String, Event> caseMap) {
        this.caseMap = caseMap;
    }
}
