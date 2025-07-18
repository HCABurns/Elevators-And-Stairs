package com.codingame.game;

import com.codingame.gameengine.module.entities.World;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    // Define view height and width.
    public static final int VIEWER_WIDTH = World.DEFAULT_WIDTH;
    public static final int VIEWER_HEIGHT = World.DEFAULT_HEIGHT;

    public static final int CELL_SIZE = 256;

    public static final Character STAIR_CHARACTER = 'S';
    public static final Character ELEVATOR_CHARACTER = 'E';
    public static final Character DOOR_CHARACTER = 'D';
    public static final Character STANDARD_CHARACTER = ' ';

    // Define Brodo
    public static final String BRODO = "test.png";
    //public static final String BRODO = "brodo.png";
    public static final int BRODO_SIZE = 256;
    //public static final int BRODO_SIZE = 32;

    // Define the basic rooms.
    public static final String BACKGROUND = "bg.png";
    public static final String STANDARD = "standard.png";
    public static final String ELEVATOR = "elevator.png";
    public static final String STAIRS_BOTTOM = "stair_bottom.png";
    public static final Map<Character, String> ROOM_MAP;
    static {
        Map<Character, String> map = new HashMap<>();
        map.put(STANDARD_CHARACTER, STANDARD);
        map.put(ELEVATOR_CHARACTER, ELEVATOR);
        map.put(STAIR_CHARACTER, STAIRS_BOTTOM);
        map.put(DOOR_CHARACTER, STANDARD);//todo: change
        ROOM_MAP = map;
        }

    // Define enum for directions of stairs and elevators.
    private final static int UP = 0;
    private final static int DOWN = 1;
    private final static int BOTH = 2;
    private final static int NONE = 3;
    public enum Direction{UP,DOWN,BOTH, NONE};


}
