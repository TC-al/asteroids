package com.company;

import java.awt.*;

public class VectorSprite { // Blueprint on how to make obj in our game.

    Polygon shape, drawShape; // Each VectorSprite will have 2 polygons , for rotation and drawing.

    double xPosition; // Position in x-axis.
    double yPosition; // Position in y-axis.
    double xSpeed; // Speed in Horizontal.
    double ySpeed; // Speed in vertical.
    double angle; // Rotates ship.
    double ROTATION; // A constant that represents the amount of change in angle.
    double thrust; // A constant that represents the amount of change in acceleration.

    boolean active; // Track if dead or alive.

    int counter; // Increase the timer tick by 1.

    public VectorSprite() { // Constructor to initialize our objects.

    }

    public void paint(Graphics g){ // The method allow us to paint our shape.

        g.drawPolygon(drawShape);

    }

    public void updatePosition() { //keeps track of our speed, position and angle.

        //Position
        counter++;

        // The position will be updated according to the speed
        xPosition += xSpeed;
        yPosition += ySpeed;
        wrapAround();

        int x, y; // Temp values to use in calculation

        // Updates the xpoints to be the same as speed
        for (int i = 0; i < shape.npoints; i++) {
            //shape.xpoints[i] += xSpeed;
            //shape.ypoints[i] += ySpeed;
            // x' = x * cos(Θ) – y * sin(Θ)
            // y' = x * sin(Θ) + y * cos(Θ)
            // Convert these formulas into code
            x = (int) Math.round(shape.xpoints[i] * Math.cos(angle) - shape.ypoints[i] * Math.sin(angle));
            y = (int) Math.round(shape.xpoints[i] * Math.sin(angle) + shape.ypoints[i] * Math.cos(angle));

            drawShape.xpoints[i] = x;
            drawShape.ypoints[i] = y;

        }

        drawShape.invalidate();
        drawShape.translate((int) Math.round(xPosition), (int) Math.round(yPosition));

}

    private void wrapAround() { // Screen wrapping for our game! Stops us from flying forever.

        if (xPosition > 900) { // If we go off on the right, we spawn on the left side

            xPosition = 0;

        }

        if (yPosition > 600) { // If we go off on the right, we spawn on the left side

            yPosition = 0;

        }

        if (xPosition < 0) { // If we go off on the right, we spawn on the left side

            xPosition = 900;

        }

        if (yPosition < 0) { // If we go off on the right, we spawn on the left side

            yPosition = 600;

        }

    }


}
