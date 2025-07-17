package com.codingame.game;

import java.util.ArrayList;

public class Map {

    // ArrayList holding the information about the rooms.
    private ArrayList<ArrayList<Room>> rooms = new ArrayList<>();
    private ArrayList<String> rooms_strings = new ArrayList<>();
    private int h;
    private int w ;

    private int y = 0;
    private int x = 0;

    // Constructor to create a board.
    public Map(int h, int w){
        this.h = h;
        this.w = w;
    }

    /**
     * Function to add levels to the map.
     *
     * @param level Get the level as a string.
     * @param y Get the Y index of the level.
     */
    public void addLevel(String level, int y){
        rooms_strings.add(level.replace('B',' '));
        ArrayList<Room> level_rooms = new ArrayList<>();
        for (int x = 0; x <level.length(); x++){
            char element = level.charAt(x);
            if (element == 'B'){element = ' '; this.y = y; this.x = x;}
            level_rooms.add(new Room(x, y, element));
        }
        rooms.add(level_rooms);
    }

    /**
     * Function to move the player depending on provided action.
     * @param action Direction of travel.
     */
    public void move(String action){
        if (action.equals("L") || action.equals("R")){
            moveSideways(action);
        }
        if (action.equals("UP") || action.equals("DOWN")){
            moveVertical(action);
        }
    }

    /**
     * This will move the player sideways.
     * @param action Direction of travel.
     */
    public void moveSideways(String action){
        if (action.equals("L")){
            x -= 1;
        }else{
            x += 1;
        }
    }

    /**
     * This will move the player vertically using a elevator or stairs.
     * @param action - Direction of motion.
     */
    public void moveVertical(String action){
        Room room = rooms.get(y).get(x);
        if (room.isIs_elevator()){
            if (action.equals("UP") && (room.getDirection() == Constants.Direction.UP || room.getDirection() == Constants.Direction.BOTH)){
                this.y = room.getAboveY();
            }else if (action.equals("DOWN") && (room.getDirection() == Constants.Direction.DOWN || room.getDirection() == Constants.Direction.BOTH)){
                this.y = room.getBelowY();
            }
        }
        if (room.isIs_stairs()){
            if (action.equals("UP") && (room.getDirection() == Constants.Direction.UP || room.getDirection() == Constants.Direction.BOTH)){
                this.y = room.getAboveY();
                this.x = room.getAboveX();
            }else if (action.equals("DOWN") && (room.getDirection() == Constants.Direction.DOWN || room.getDirection() == Constants.Direction.BOTH)){
                this.y = room.getBelowY();
                this.x = room.getBelowX();
            }
        }
    }


    /**
     * Sets the available to directions for stairs and elevators. Set doors availability as well.
     */
    public void setDirections(){
        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                Room room = rooms.get(i).get(j);
                if (room.getRoom() == 'E'){
                    room.setIs_elevator(true);
                    int above = isElevatorAbove(i,j, 'E');
                    int below = isElevatorBelow(i,j, 'E');
                    if (above >= 0 && below >= 0){
                        room.setDirection(Constants.Direction.BOTH);
                        room.addAboveY(above);
                        room.addBelowY(below);
                    }else if (above >= 0){
                        room.setDirection(Constants.Direction.UP);
                        room.addAboveY(above);
                    }else if (below >= 0){
                        room.setDirection(Constants.Direction.DOWN);
                        room.addBelowY(below);
                    }
                }
                else if (room.getRoom() == 'S'){
                    room.setIs_stairs(true);
                    int[] above = isStairsAbove(i,j, 'S');
                    int[] below = isStairsBelow(i,j, 'S');
                    if (above[0] >= 0 && below[0] >= 0){
                        room.setDirection(Constants.Direction.BOTH);
                        room.addAboveY(above[0]);
                        room.addAboveX(above[1]);
                        room.addBelowY(below[0]);
                        room.addBelowX(below[1]);
                    }else if (above[0] >= 0){
                        room.setDirection(Constants.Direction.UP);
                        room.addAboveY(above[0]);
                        room.addAboveX(above[1]);
                    }else if (below[0] >= 0){
                        room.setDirection(Constants.Direction.DOWN);
                        room.addBelowY(below[0]);
                        room.addBelowX(below[1]);
                    }
                }
                else if (room.getRoom() == 'D') {
                    room.setIs_door(true);
                }
            }
        }
    }

    /**
     * Check if there is accessible stairs from below pos [y,x].
     * @param y Y position / Level.
     * @param x X coordinates of stairs.
     * @param character Character to check for.
     * @return Position of stairs below that connect or [-1,-1] if none.
     */
    public int[] isStairsBelow(int y, int x, int character){
        int sideways = 1;
        for (int i = y-1; i >= 0; i--){
            if (x+sideways >= 0 && x+sideways < w && rooms.get(i).get(x+sideways).getRoom() == character){
                return new int[]{i,x+sideways};
            }
            if (x-sideways >= 0 && x-sideways < w && rooms.get(i).get(x-sideways).getRoom() == character){
                return new int[]{i,x-sideways};
            }
            sideways += 1;
        }
        return new int[]{-1,-1};
    }

    /**
     * Check if there is accessible stairs from above pos [y,x].
     * @param y Y position / Level.
     * @param x X coordinates of stairs.
     * @param character Character to check for.
     * @return Position of stairs above that connect or [-1,-1] if none.
     */
    public int[] isStairsAbove(int y, int x, int character){
        int sideways = 1;
        for (int i = y+1; i < h; i++){
            if (x+sideways >= 0 && x+sideways < w && rooms.get(i).get(x+sideways).getRoom() == character){
                return new int[]{i,x+sideways};
            }
            if (x-sideways >= 0 && x-sideways < w && rooms.get(i).get(x-sideways).getRoom() == character){
                return new int[]{i,x-sideways};
            }
            sideways += 1;
        }
        return new int[]{-1,-1};
    }

    /**
     * Check if there is accessible elevator from below pos [y,x].
     * @param y Y position / Level.
     * @param x X coordinates of elevator.
     * @param character Character to check for.
     * @return Y-Position of elevator below that connects or -1 if none.
     */
    public int isElevatorBelow(int y, int x, char character){
        for (int i = y-1; i >= 0; i--){
            if (rooms.get(i).get(x).getRoom() == character){
                return i;
            }
        }
        return -1;
    }

    /**
     * Check if there is accessible elevator from above pos [y,x].
     * @param y Y position / Level.
     * @param x X coordinates of elevator.
     * @param character Character to check for.
     * @return Y-Position of elevator above that connects or -1 if none.
     */
    public int isElevatorAbove(int y, int x, char character){
        for (int i = y+1; i < h; i++){
            if (rooms.get(i).get(x).getRoom() == character){
                return i;
            }
        }
        return -1;
    }

    /**
     * Check if current position is at a door.
     * @return Boolean - True if at door otherwise false.
     */
    public boolean isAtDoor(){
        return rooms.get(y).get(x).isIs_door();
    }

    public Room getCell(int y, int j){return rooms.get(y).get(j);}

    public ArrayList<Room> getRoom(int y){
        return rooms.get(y);
    }

    public String getRoomString(int y){
        return rooms_strings.get(y);
    }

    public ArrayList<ArrayList<Room>> getRooms(){
        return this.rooms;
    }

    public int[] getBrodoPosition(){
        return new int[]{y,x};
    }

    public int getHeight() {
        return h;
    }

    public int getWidth() {
        return w;
    }

    public void printMap(){
        for (int i = rooms.size()-1; i > - 1; i--){
            System.err.println(rooms.get(i));
        }
    }
}
