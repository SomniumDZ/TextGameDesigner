package pawn;

import pawn.inventory.Item;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class NPC {
    private LinkedHashMap<String, String[]> dialogues;
    private LinkedList<Item> inventory;

    public NPC() {
        dialogues = new LinkedHashMap<>();
    }
}
