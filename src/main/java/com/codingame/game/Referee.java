package com.codingame.game;
import java.util.*;

import com.codingame.game.exceptions.Warning;
import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.SoloGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.viewport.ViewportModule;
import com.google.inject.Inject;
import com.codingame.game.modules.Renderer;

public class Referee extends AbstractReferee {
    // Uncomment the line below and comment the line under it to create a Solo Game
    @Inject private SoloGameManager<Player> gameManager;
    @Inject private GraphicEntityModule graphicEntityModule;
    @Inject private Renderer renderer;
    @Inject private ViewportModule viewportModule;
    private Map map;
    private Set<String> validMoves = new HashSet<>();

    private String errorMessage = "An Unexpected Error Occurred.";

    @Override
    public void init() {
        // Initialize your game here.
        // Set frame duration.
        gameManager.setFrameDuration(600);
        gameManager.setFirstTurnMaxTime(2000);
        gameManager.setMaxTurns(50);
        gameManager.setTurnMaxTime(50);

        // Get inputs from the case and send to user.
        int width = Integer.parseInt(gameManager.getTestCaseInput().get(0));
        int height = Integer.parseInt(gameManager.getTestCaseInput().get(1));

        // Create the board. (Board in input but is NOT sent to the user).
        map = new Map(height, width);
        for (int i = 2; i < gameManager.getTestCaseInput().size(); i++){
            map.addLevel(gameManager.getTestCaseInput().get(i), i - 2);
        }

        // Set the directions for elevators and stairs.
        map.setDirections();

        System.out.println("Directions Below:");
        map.printMap();

        // Get the inputs and send the required information to the user. (Room width and first room)
        gameManager.getPlayer().sendInputLine(String.valueOf(width));

        // Set the visuals using Renderer.
        renderer.setMap(map.getRooms());

        // Set the brodo visuals...
        renderer.setBrodo(map.getBrodo());
        renderer.updateBrodo(map.getBrodo());

        // Scale group
        renderer.scaleGroup(map.getWidth(), map.getHeight());
        renderer.getGroup().setZIndex(20);

        //Add group to the viewport.
        viewportModule.createViewport(renderer.getGroup());

        // Set valid moves.
        setValidMoves();

    }

    @Override
    public void gameTurn(int turn) {
        // Send the user Brodo's position and the level he's currently on.
        int[] brodo = map.getBrodoPosition();
        int y = brodo[0];
        int x = brodo[1];
        System.out.println(x+" "+y);
        System.out.println(map.getRoomString(y));
        gameManager.getPlayer().sendInputLine(x+" "+y);
        gameManager.getPlayer().sendInputLine(map.getRoomString(y).toString());
        gameManager.getPlayer().execute();


        try {
            // Check validity of the player output.
            List<String> outputs = gameManager.getPlayer().getOutputs();
            checkOutputs(outputs);
            String action = outputs.get(0);

            // Check game is over or not
            if (map.isAtDoor() && action.equals("OPEN")){
                gameManager.winGame("Brodo has reached the fires of Mount Boom!");
            }

            System.out.println("Before");
            System.out.println(Arrays.toString(map.getBrodoPosition()));

            // Move player
            map.move(action);

            System.out.println("After");
            System.out.println(Arrays.toString(map.getBrodoPosition()));

            // Update Visual
            renderer.updateBrodo(map.getBrodo());
        }
        catch (TimeoutException e){
            errorMessage = "Timeout...";
            gameManager.loseGame(errorMessage);
        }
        catch (Warning e){
            System.err.println(e.getMessage());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            gameManager.loseGame(errorMessage);
        }
    }


    public void checkOutputs(List<String> outputs) throws Exception{

        if (outputs.size() != 1){gameManager.loseGame("You did not send a single input.");}
        System.err.println(outputs);

        int[] brodo = map.getBrodoPosition();
        int y = brodo[0];
        int x = brodo[1];

        String output = outputs.get(0);

        if (!validMoves.contains(output)){
            errorMessage = "Invalid Option Provided.";
            throw new Exception("Invalid Input Error");
        }

        //todo: Check for invalid moves... (if invalid allow but don't do?? or end game??)

        // Tried to go down but no option to go down.
        if (output.equals("DOWN") && (map.getCell(y,x).getDirection() != Constants.Direction.BOTH && map.getCell(y,x).getDirection() != Constants.Direction.DOWN)){
            errorMessage = "Unable to go down!";
            throw new Warning(errorMessage);
        }

        // Tried to go up but no option to go up.
        System.out.println(map.getCell(y,x).getRoom() + " " +y + " " + x + " " + output + " " + map.getCell(y,x).getDirection());
        if (output.equals("UP") && (map.getCell(y,x).getDirection() != Constants.Direction.BOTH && map.getCell(y,x).getDirection() != Constants.Direction.UP)){
            errorMessage = "Unable to go up!";
            throw new Warning(errorMessage);
        }

        // Tried to go right/left but can't go right/left.
        if (output.equals("L") && x == 0 || output.equals("R") && x == map.getWidth()-1){
            errorMessage = "There is a wall in your way!";
            throw new Warning(errorMessage);
        }

        // Tried to open but no door.
        if (output.equals("OPEN") && !map.getCell(y,x).isIs_door()){
            errorMessage = "There is no door to open!";
            throw new Warning(errorMessage);
        }
    }

    public void setValidMoves(){
        validMoves.add("L");
        validMoves.add("R");
        validMoves.add("UP");
        validMoves.add("DOWN");
        validMoves.add("OPEN");
    }

}
