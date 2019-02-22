/*
    Superclass for GObjects with basic methods for x/y coordinate
    and height/width retrieval.
 */
package edu.stanford.cs108.mobiledraw;

import android.graphics.Canvas;

public class GObject {

    private float xCoor;
    private float yCoor;
    private float width;
    private float height;
    /*
    Constructor
     */
    public GObject (float xCoor, float yCoor, float width, float height) {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.width = width;
        this.height = height;
    }

    public float getxCoor() {
        return xCoor;
    }

    public float getyCoor() {
        return yCoor;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void drawShape(Canvas canvas, boolean isSelected) {
        System.out.println("I'm just a plain old GObject. I can't be drawn!");
    }

    public void setxCoor(float xCoor) {
        this.xCoor = xCoor;
    }

    public void setyCoor(float yCoor) {
        this.yCoor = yCoor;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setwidth(float width) {
        this.width = width;
    }

}
