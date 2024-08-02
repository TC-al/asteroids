package com.company;

import java.awt.*;

public class Asteroids extends VectorSprite { // Enemies class and we inherit from Vector.

    int size; // Sets the size of the asteroid.

    public Asteroids() {

        size = 3; // Sets the original size to be 3.

        initializeAsteroid();

    }

    public Asteroids(double x, double y, int s) { // This will be our second constructor. This will adjust the new asteroids to the same spot.

        size = s;

        initializeAsteroid();

        xPosition = x;
        yPosition = y;

    }

    public void initializeAsteroid() { // Stops us from rewriting the same code in both our constructors.

        shape = new Polygon();
        shape.addPoint(15 * size, 3 * size);
        shape.addPoint(5 * size, 17 * size);
        shape.addPoint(-12 * size, 5 * size);
        shape.addPoint(-8 * size, -8 * size);
        shape.addPoint(10 * size, -17 * size);

        drawShape = new Polygon();
        drawShape.addPoint(15 * size, 3 * size);
        drawShape.addPoint(5 * size, 17 * size);
        drawShape.addPoint(-12 * size, 5 * size);
        drawShape.addPoint(-8 * size, -8 * size);
        drawShape.addPoint(10 * size, -17 * size);

        ROTATION = Math.random() / (size * 5);
        thrust = 2;

        double h, a; // Hypotenuse and angle that we need for our asteroids
        h = Math.random() + (thrust / size); // +1 allows our speed to change at a faster rate.
        a = Math.random() * (2 * Math.PI); // With 2PI we have a full range of movement.

        xSpeed = Math.cos(a) * h; // The cos gives us xSpeed. Used trig to find it out.
        ySpeed = Math.sin(a) * h; // The sin gives us ySpeed. Used trig to find it out.

        h = Math.random() * 400 + 100;
        a = Math.random() * (2 * Math.PI);

        xPosition = Math.cos(a) * h + 450;
        yPosition = Math.sin(a) * h + 300;

        active = true;

    }

    public void updatePosition() { // Change the angle automatically.

        angle += ROTATION;
        super.updatePosition();

    }

    }
