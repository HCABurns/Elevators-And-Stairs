package com.codingame.game;

import com.codingame.gameengine.module.entities.World;

public class Constants {

    // Define view height and width.
    public static final int VIEWER_WIDTH = World.DEFAULT_WIDTH;
    public static final int VIEWER_HEIGHT = World.DEFAULT_HEIGHT;

    private static int UP = 0;
    private static int DOWN = 1;
    private static int BOTH = 2;
    private static int NONE = 3;
    public enum Direction{UP,DOWN,BOTH, NONE};


}
