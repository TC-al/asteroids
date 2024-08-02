package com.company;

import java.awt.*;

public class Bullet extends VectorSprite {

    public Bullet(double shipX, double shipY, double shipA) { // Constructor for out Bullet class. We need to take in 3 double values we call the constructor.

        shape = new Polygon();

        shape.addPoint(1, 1);
        shape.addPoint(-1, 1);
        shape.addPoint(-1, -1);
        shape.addPoint(1, -1);

        drawShape = new Polygon();

        drawShape.addPoint(1, 1);
        drawShape.addPoint(-1, 1);
        drawShape.addPoint(-1, -1);
        drawShape.addPoint(1, -1);

        // Bullet come from ships, so they have the same coordinates.
        xPosition = shipX;
        yPosition = shipY;
        angle = shipA;

        // Speed of our bullets
        thrust = 10;
        xSpeed = Math.cos(angle) * thrust;
        ySpeed = Math.sin(angle) * thrust;

        active = true;

    }

}
