package com.company;

import java.awt.*;

public class Debris extends VectorSprite {

    public Debris(double shipX, double shipY) { // Constructor for out Debris class. We need to take in 3 double values we call the constructor.

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

        // Debris come from ships, so they have the same coordinates.
        xPosition = shipX;
        yPosition = shipY;

        // Speed of our debris
        thrust = 10;

        // We made the angle (a) be a random number so that it can break off into random spots.
        double a;
        a = Math.random() * 2 * Math.PI;
        xSpeed = Math.cos(a) * a;
        ySpeed = Math.sin(a) * a;

        active = true;

    }

}
