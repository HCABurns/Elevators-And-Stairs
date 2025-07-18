package com.codingame.game;

import com.codingame.gameengine.module.entities.Sprite;

public class Brodo {

    int y = 0;
    int x = 0;
    Sprite sprite;

    public Brodo(int y, int x){
        this.y = y;
        this.x = x;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    @Override
    public String toString() {
        return "Brodo{" +
                "y=" + y +
                ", x=" + x +
                '}';
    }
}
