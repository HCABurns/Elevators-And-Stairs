package com.codingame.game.modules;

import com.codingame.game.Brodo;
import com.codingame.game.Constants;
import com.codingame.game.Player;
import com.codingame.game.Room;
import com.codingame.gameengine.core.Module;
import com.codingame.gameengine.core.SoloGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;
import com.google.inject.Inject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Renderer implements Module {

    private final SoloGameManager<Player> gameManager;
    private final List<Map<String, Serializable>> allRooms = new ArrayList<>();
    private final GraphicEntityModule graphicEntityModule;
    private final Group group;
    private ArrayList<ArrayList<Room>> rooms = new ArrayList<>();// Constants
    private Brodo previousBrodo;
    private Sprite brodoSprite;
    private double scaleX;
    private double scaleY;

    @Inject
    public Renderer(SoloGameManager<Player> gameManager, GraphicEntityModule graphicEntityModule) {
        this.gameManager = gameManager;
        this.graphicEntityModule = graphicEntityModule;
        int z_BACKGROUND = 0;
        graphicEntityModule.createSprite().setImage(Constants.BACKGROUND).setZIndex(z_BACKGROUND);
        group = graphicEntityModule.createGroup();
        gameManager.registerModule(this);
    }

    public void setMap(ArrayList<ArrayList<Room>> rooms){
        this.rooms = rooms;
        for (int i = 0; i < rooms.size(); i++){
            ArrayList<Room> level = rooms.get(i);
            for (int j = 0; j < level.size(); j++){
                //todo: Alter for stairs top and bottom.
                String tileName = Constants.STANDARD;
                if (Constants.ROOM_MAP.containsKey(level.get(j).getRoom())){
                    tileName = Constants.ROOM_MAP.get(level.get(j).getRoom());
                }
                System.out.println("Room: " + level.get(j).getRoom() + " " +Constants.ROOM_MAP.containsKey(level.get(j).getRoom()) + " Tlename: " + tileName);
                int z_TILES = 5;
                Sprite room = graphicEntityModule.createSprite()
                        .setImage(tileName)
                        .setX(j * Constants.CELL_SIZE)
                        .setY((rooms.size()-i-1) * Constants.CELL_SIZE)
                        .setAnchor(0)
                        .setZIndex(z_TILES);
                addRoom(room.getId(), room.getImage(), level.get(j).getRoom());
                group.add(room);
            }
        }
    }

    public void setBrodo(Brodo brodo){
        //todo: Make him "walk" in from the left hand side to his starting position.
        System.out.println("Brodo Starting position: " + brodo.getX() + " Y: " + (rooms.size()-brodo.getY()-1));
        brodoSprite = graphicEntityModule.createSprite()
                .setImage(Constants.BRODO)
                .setX((brodo.getX()-1) * Constants.CELL_SIZE)
                //.setY((rooms.size()-brodo.getY()-1) * Constants.CELL_SIZE)
                .setY(0)
                .setAnchor(0)
                .setZIndex(10);
        //brodoSprite.setScale((double) Constants.CELL_SIZE / Constants.BRODO_SIZE);
        brodo.setSprite(brodoSprite);
        group.add(brodoSprite);
        previousBrodo = brodo;
    }

    public void addRoom(int id, String texture, char number){
        Map<String, Serializable> tile = new HashMap<>();
        tile.put("id", id);
        tile.put("texture", texture);
        tile.put("room", number);
        allRooms.add(tile);
    }

    public Group getGroup() {
        return group;
    }

    public void scaleGroup(int w, int h){
        // Calculate total grid size in pixels
        int gridWidth = w * Constants.CELL_SIZE;
        int gridHeight = h * Constants.CELL_SIZE;

        // Calculate scale to fit in viewer
        double scaleX = (double) Constants.VIEWER_WIDTH / gridWidth;
        double scaleY = (double) Constants.VIEWER_HEIGHT / gridHeight;
        double scale = Math.min(1.0, Math.min(scaleX, scaleY));

        // Recompute size after scale for centering
        int scaledWidth = (int) (gridWidth * scale);
        int scaledHeight = (int) (gridHeight * scale);

        // Center the group in the viewer
        int centerX = Constants.VIEWER_WIDTH / 2;
        int centerY = Constants.VIEWER_HEIGHT / 2;

        this.scaleX = scaleX;
        this.scaleY = scaleY;
        group.setScale(scale);
        group.setX(centerX - scaledWidth / 2);
        group.setY(centerY - scaledHeight / 2);
        System.out.println((centerX - scaledWidth / 2) + " |||| " + (centerY - scaledHeight / 2));
    }

    public void updateBrodo(Brodo brodo){
        Map<String, Serializable> br = new HashMap<>();

        Map<String, Serializable> b = new HashMap<>();
        b.put("id", brodoSprite.getId());
        b.put("x", brodo.getX());
        b.put("y",  brodo.getY());
        b.put("prevX", previousBrodo.getX());
        b.put("prevY", previousBrodo.getY());
        b.put("scaleY", scaleY);
        b.put("scaleX", scaleX);
        br.put("brodo", (Serializable) b);

        System.out.println(brodo + " ::: " + previousBrodo);

        gameManager.setViewData("Renderer", br);
        previousBrodo = new Brodo(brodo.getY(), brodo.getX());

    }

    @Override
    public void onGameInit() {

    }

    @Override
    public void onAfterGameTurn() {

        //gameManager.setViewData("Blah", allRooms);

        // Move brodo with the renderer.js

    }

    @Override
    public void onAfterOnEnd() {

    }

    public int getZ_UI() {
        return 5;
    }
}
