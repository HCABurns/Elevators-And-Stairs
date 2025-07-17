package com.codingame.game;

public class Room {

    private int x;
    private int y;
    private char room;

    private boolean is_stairs = false;
    private boolean is_elevator = false;
    private boolean is_door = false;

    private int aboveY = -1;
    private int aboveX = -1;
    private int belowY = -1;
    private int belowX = -1;

    private Constants.Direction direction;

    public Room(int x, int y, char room) {
        this.x = x;
        this.y = y;
        this.room = room;
        this.direction =  Constants.Direction.NONE;
    }

    @Override
    public String toString() {
        return String.valueOf(this.direction);
        //return String.valueOf(this.room);
    }


    public void addAboveY(int y){
        aboveY = y;
    }

    public void addBelowY(int y){
        belowY = y;
    }

    public void addAboveX(int x){
        aboveX = x;
    }

    public void addBelowX(int x){
        belowX = x;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getRoom() {
        return room;
    }

    public void setRoom(char room) {
        this.room = room;
    }

    public boolean isIs_stairs() {
        return is_stairs;
    }

    public void setIs_stairs(boolean is_stairs) {
        this.is_stairs = is_stairs;
    }

    public boolean isIs_elevator() {
        return is_elevator;
    }

    public void setIs_elevator(boolean is_elevator) {
        this.is_elevator = is_elevator;
    }

    public boolean isIs_door() {
        return is_door;
    }

    public void setIs_door(boolean is_door) {
        this.is_door = is_door;
    }

    public Constants.Direction getDirection() {
        return direction;
    }

    public void setDirection(Constants.Direction direction) {
        this.direction = direction;
    }



    public int getAboveY() {
        return aboveY;
    }

    public int getBelowY(){
        return belowY;
    }

    public int getAboveX() {
        return aboveX;
    }

    public int getBelowX(){
        return belowX;
    }
}
