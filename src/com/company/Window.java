package com.company;

import javax.swing.*;
import java.awt.*;

public class Window extends JPanel {

    private Game game; // Created an instance of the Game Class.

    Color[] debrisColor = {Color.WHITE, Color.ORANGE, Color.YELLOW, Color.RED}; // The options for debris color.
    Color[] asteroidColor = {Color.ORANGE, Color.YELLOW, Color.RED}; // The options for asteroid color.

    int randNum;
    int colorTimer = 0; // Stops the color from flashing.

    int randDebrisNum;

    public Window(Game game) {

        this.game = game;

        setPreferredSize(new Dimension(900, 600)); // Set size of the screen
        setBackground(Color.BLACK); // Sets background color to black

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g); // makes it so our window isn't see through.

        // Draws the image the first time for double buffering
        game.offg.setColor(Color.BLACK);
        game.offg.fillRect(0, 0, 900, 600); // draws a rectangle in the background of the screen.
        game.offg.setColor(Color.WHITE); // Sets the line colour to the color that it says.
        game.offg.drawString("Lives " + game.ship.lives, 20, 40);
        game.offg.drawString("Score: " + game.score, 20, 20);
        game.offg.drawString("High Score: " + game.currHighScore, 800, 30);

        if (game.ship.active) { // If the ship doesn't touch the asteroid, then draw the ship

            game.ship.paint(game.offg); // Draws the ship to the screen.

        }

        for (int i = 0; i < game.asteroidList.size(); i++) { // Gets all the asteroids from the List and draws it.

            if (game.ship.lives > 0) { //Stops drawing the asteroids if we have no lives.

                game.offg.setColor(asteroidColor[2]); // Sets the colour for the asteroid.

                game.offg.fillPolygon(game.asteroidList.get(i).drawShape); // FILL IN THE COLOR.
                game.offg.setColor(Color.pink); // Set the colour of our outline.
                //FIXME: CHALLENGE: Implement a counter so that the color changes based on that.
                game.asteroidList.get(i).paint(game.offg);


            }
        }

        game.offg.setColor(Color.WHITE); // Sets colour of bullet.
        for (int i = 0; i < game.bulletList.size(); i++) { // Checks each bullet.

            game.bulletList.get(i).paint(game.offg); // Draws the bullet to the screen.

        }

        for (int i = 0; i < game.debrisList.size(); i++) {

            randNum = (int) (Math.random() * debrisColor.length); // Makes a dom number from the size of debris.

            game.offg.setColor(debrisColor[randNum]); // We choose the random number FROM the debrisColor array.
            game.debrisList.get(i).paint(game.offg);

        }

        // Game Ending Messages!
        if (game.beatLevel == true) { // If the asteroidList is empty then print out Game won message.

            game.offg.drawString("Beat Level " + game.currlevel + "!", 450, 264);
            game.offg.drawString("Press n to move to next level", 415, 300);

        }
        // FIXME : This does not work cause we die right after.
        if (game.gameActive == false) {

            game.offg.drawString("Game Over", 400, 300);

        }

        // To draw the image a second time
        g.drawImage(game.offscreen, 0, 0, this);
        repaint();

    }

}
