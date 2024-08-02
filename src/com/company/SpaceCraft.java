package com.company;

import java.awt.*;

public class SpaceCraft extends VectorSprite { // We inherited all te code from Vector Sprite so we have everything Vector Sprite does.

    // Copied the blueprint.

    int lives; // This will make lives only inside Spacecraft.

    public SpaceCraft() {

        shape = new Polygon(); // Created an instance of the Polygon class.

        shape.addPoint(15, 0);
        shape.addPoint(-10, 10);
        shape.addPoint(-4, 0);
        shape.addPoint(-10, -10);

        drawShape = new Polygon(); // Creates an instance of Polygon class.
        drawShape.addPoint(15, 0);
        drawShape.addPoint(-10, 10);
        drawShape.addPoint(-4, 0);
        drawShape.addPoint(-10, -10);

        xPosition = 450;
        yPosition = 300;

        ROTATION = 0.1;
        thrust = 0.25;

        active = true;
        lives = 3; // Amount of lives we have.

    }

    public void accelerate() { // Checks how fast we are going. Checks the xSpeed and ySpeed.

        xSpeed += Math.cos(angle) * thrust; // Finds the unknown xSpeed. Cos = opposite/adjacent
        ySpeed += Math.sin(angle) * thrust; // Finds the unknown ySpeed. Sin = opposite/hypotenuse

    }

    public void rotateRight() { // Change the angle to decrease.

        angle += ROTATION;

    }

    public void rotateLeft() { // Change the angle to increase.

        angle -= ROTATION;

    }

    public void hit() { //Check if we have been hit or not

        active = false;
        counter = 0; // Refresh the counter if we are hit
        lives--; // If hit then lose a life.

    }

    public void respawn() {

            xPosition = 450;
            yPosition = 300;
            xSpeed = 0;
            ySpeed = 0;
            angle = 0;
            active = true;

    }

}
