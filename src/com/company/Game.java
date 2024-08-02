package com.company;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.io.FileWriter; // This allows us to write in files.
import java.io.IOException; // This allows us to check for errors.
import java.util.Scanner;


public class Game extends JFrame implements KeyListener, ActionListener {

    public Window panel; // Creates an instance of the Window called "Panel".
    SpaceCraft ship; // Creates instance of the VectorSprite called "ship".
    Timer timer; // Keeps tract of our speed and position.
    Image offscreen; // An image to be loaded offscreen.
    Graphics offg; // Graphics object to go along with the offscreen image.
    AudioUtil au; // Used to load in audio files.
    AudioClip laser;
    AudioClip thruster;
    AudioClip explode1;
    AudioClip explode;
    AudioClip theme;

    ArrayList<Asteroids> asteroidList; // List of asteroid objects.

    ArrayList<Bullet> bulletList; //A list of bullet objects.

    ArrayList<Debris> debrisList; // A list of debris.

    int score; // The score for our game.

    int asteroidTotal; // The total amount of asteroids in out game.

    boolean upKey, rightKey, leftKey, spaceKey, rKey, nKey, backSpace; // Fixes key locking.

    boolean gameActive;

    boolean beatLevel;

    int bulletRateOfFire;

    int currlevel; // The current level you are on.

    int ROF_ASTEROIDS; // Starting asteroids constant.

    int currHighScore;

    public void init() throws MalformedURLException {

        UI();
        buffer();
        loadObjects();
        loadSounds();
        loadTimer();
        gameActive = true;

    }

    public void UI() {

        this.setVisible(true);
        this.setSize(900,600); // Set size of the game screen.
        this.setTitle("Asteroids - Alan"); // allows us to set a title for the game window.
        this.setResizable(false); //Stops a user from resizing the window.
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Closes the game so it doesn't run in the background.
        this.addKeyListener(this); // Allows us to listen to key commands.

    }

    public void buffer() {

        add(this.panel = new Window(this), BorderLayout.CENTER); // Adds a layer on top of our game screen that acts as a panel to adjust size to what we want. Acts as our paint method.
        //Double Buffering
        offscreen = createImage(this.getWidth(), this.getHeight()); // Offscreen Image.
        offg = offscreen.getGraphics(); // Offscreen graphics object.

    }

    public void loadObjects() {

        ship = new SpaceCraft(); // Init the ship to the Spacecraft.
        asteroidList = new ArrayList(); // Creates an instance of the List.

        asteroidTotal = 5; // Starting amount of asteroids in Level 1.
        loadAsteroids();

        bulletList = new ArrayList();
        ROF_ASTEROIDS = 5;
        bulletRateOfFire = ROF_ASTEROIDS; // Starting bullet rate of fire before we slow it down in level 2.
        debrisList = new ArrayList();

        score = 0;
        currlevel = 1;

        writeToFile();

    }

    public void loadAsteroids() { // Allows us to draw and set the total amount of asteroids.

        for (int i = 0; i < asteroidTotal; i++){ // Sets the amount of asteroids we want to draw.

            asteroidList.add(new Asteroids());

        }

    }

    public void loadTimer() {

        timer = new Timer(5, this); // Init timer.
        pack(); // Built-in function that makes Window class fit the size and layout of subcomponents.
        timer.start(); // Starts the timer.

    }

    public void loadSounds() throws MalformedURLException {

        au = AudioUtil.getInstance(); // This will set au to an instance.

        laser = Applet.newAudioClip(au.transform((new File("./src/sounds/laser80.wav"))));
        thruster = Applet.newAudioClip(au.transform((new File("./src/sounds/thruster.wav"))));
        explode = Applet.newAudioClip(au.transform((new File("./src/sounds/explode0.wav"))));
        explode1 = Applet.newAudioClip(au.transform((new File("./src/sounds/explode1.wav"))));
        theme = Applet.newAudioClip(au.transform((new File("./src/sounds/spacetheme.mp3"))));

    }

    public void respawnShip() { // Respawns the ship

        if (ship.active == false && ship.counter > 49.3141592653589323 && isRespawnSafe() && ship.lives > 0) { // To change the ship respawn time, change the number

            ship.respawn();

        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.print(e.getKeyCode()); //tells us what key we pressed

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) { // If the keycode right arrow is equal to the key you pressed then do something

            rightKey = true;

        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) { // If the keycode left arrow is equal to the key you pressed then do something

            leftKey = true;

        }

        if (e.getKeyCode() == KeyEvent.VK_UP) { // If the keycode down arrow is equal to the key you pressed then do something

            upKey = true;

        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) { // Shoots the bullets.

            spaceKey = true;

        }

        if (e.getKeyCode() == KeyEvent.VK_R) { // Restarts the game.

            rKey = true;

        }

        if (e.getKeyCode() == KeyEvent.VK_N) { // Starts new level.

            nKey = true;

        }

        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) { // Cheat for killing asteroid.

            backSpace = true;

        }

        repaint();

    }

    private void keyCheck() { // Stops key locking from happening.

        if (upKey && ship.active) {

            ship.accelerate();
            thruster.play();

        }

        if (leftKey) {

            ship.rotateLeft();

        }

        if (rightKey) {

            ship.rotateRight();

        }

        if (spaceKey) {

            fireBullet();

        }

        if (rKey && gameActive == false) {

            reset();

        }

        if (nKey && beatLevel) {

            levels();
            beatLevel = false;

        }

        if (backSpace) {

            asteroidList.clear();

        }

    }

    public void reset() { // Resets out game.

        gameActive = true;
        currlevel = 1;
        score = 0;
        ship.lives = 3; // Sets the lives back to 3.
        asteroidList.clear(); // Removes all the asteroids.

        asteroidTotal = 5; // Sets the size on reset for asteroids.
        loadAsteroids(); // Redraws all the asteroids.

    }

    private void fireBullet() {

        if (ship.counter > bulletRateOfFire && ship.active) { // Allows us to have a cool down on shooting
            bulletList.add(new Bullet(ship.drawShape.xpoints[0], ship.drawShape.ypoints[0], ship.angle));
            ship.counter = 0;
            laser.play();

        }

    }

    public boolean collision(VectorSprite thingShip, VectorSprite thingRock) { // Makes ship and asteroid touch each other

        int x, y;
        // Checks if the ship is touching a asteroid
        for (int i = 0; i < thingShip.drawShape.npoints; i++) {

            x = thingShip.drawShape.xpoints[i];
            y = thingShip.drawShape.ypoints[i];

            if(thingRock.drawShape.contains(x, y)) { // If the asteroids touches the ship then.

                return true;

            }

        }
        for (int i = 0; i < thingRock.drawShape.npoints; i++) {
            x = thingRock.drawShape.xpoints[i];
            y = thingRock.drawShape.ypoints[i];

            if (thingShip.drawShape.contains(x, y)) { // If the asteroids touches the ship then.

                return true;

            }
            // Check if rock is touching a ship.
        }


        return false;
    }

    public void checkCollision() {

        double randomNum;

        for (int i = 0; i < asteroidList.size(); i++) {

            if (collision(ship, asteroidList.get(i)) && ship.active) { // Checks for collision with any of the rocks.

                score /= 2; // If you get hit then lose half points.
                ship.hit();
                explode1.play();
                randomNum = Math.random() * 5 + 5; // THis will give us a number between 5-10.

                for (int r = 0; r < randomNum; r++) { // This will keep spawning debris form 5-10

                    debrisList.add(new Debris(ship.xPosition, ship.yPosition)); // When a ship is hit, make an explosion.

                }
            }

            //FIXME: The smaller the asteroid the more points you get for destroying it.
            for (int j = 0; j < bulletList.size(); j++) { //FIXME: missing comment

                if (collision(bulletList.get(j), asteroidList.get(i))) { // Checks for collision with any of the rocks and bullets.

                    score += 20; // If you hit an asteroid then gain points.
                    bulletList.get(j).active = false;
                    asteroidList.get(i).active = false;
                    randomNum = Math.random() * 5 + 5; // THis will give us a number between 5-10.

                    for (int r = 0; r < randomNum; r++) { // This will keep spawning debris from 5-10.

                        debrisList.add(new Debris(asteroidList.get(i).xPosition, asteroidList.get(i).yPosition)); // When an asteroid is hit, make an explosion.

                    }
                }
            }
        }

        if (ship.lives == 0) { // If the ships lives drop to 0, then we are going to update gameActive.

            gameActive = false;

        }

    }

    public boolean isRespawnSafe() { // Checks if there are nothing in the respawn radius

        int x, y, h;

        for (int i = 0; i < asteroidList.size(); i++) { // Looping through the asteroids to find the distance.

            x = (int) asteroidList.get(i).xPosition - 450; // Finding one side.
            y = (int) asteroidList.get(i).yPosition - 300; // Finding the other side.
            h = (int) Math.sqrt( x * x + y * y ); // Pythagorean theorem

            if (h < 100) { // Check if an asteroid is within 100 pixels of our ship

                return false;

            }

        }

        return true;

    }

    public void checkAsteroidDestruction() { // It removes dead asteroids.

        for (int i = 0; i < asteroidList.size(); i++) { // Checks all the asteroids in the list.

            if (asteroidList.get(i).active == false) { // Check if asteroid is dead.

                //FIXME: Change to for-loop to make more asteroids as they break up.

                if (asteroidList.get(i).size > 1) {

                    asteroidList.add(new Asteroids(asteroidList.get(i).xPosition, asteroidList.get(i).yPosition, asteroidList.get(i).size - 1)); // Creates a new asteroid.
                    asteroidList.add(new Asteroids(asteroidList.get(i).xPosition, asteroidList.get(i).yPosition, asteroidList.get(i).size - 1)); // Creates a second new asteroid.
                }

                asteroidList.remove(i); // Removes the asteroid if it is dead.

                explode.play();

            }

        }

        if (asteroidList.isEmpty() == true) { // If you cleared all of the asteroids then you beat the level.

            beatLevel = true;

        }

    }

    public void levels() { // Changes different aspects of our game.

        gameActive = true; // Turns game back on.
        beatLevel = false; // Stops you from keep pressing N.
        currlevel++; // Increase the level as you level up.

        bulletRateOfFire = ROF_ASTEROIDS * currlevel; // Make the bullets slower to fire. 5 is the basic ROF (Rate of Fire).

        bulletList.clear(); // Clears the bullets when you move to the next level.

        asteroidTotal+= currlevel; // Make more asteroids.
        loadAsteroids();
        ship.thrust -= 0.05; // Makes the movement speed a bit lower.


    }

    public void writeToFile() { // Writes our file for highscores to keep track of them.

        try {

            File checkFile = new File("highScore.txt");
            if (checkFile.createNewFile()) {

                System.out.println("File Created.");

            }

            else {

                System.out.println("File Exists.");

            }

            Scanner readFile = new Scanner(checkFile); // This is will let you read this file.

            FileWriter writeFile = new FileWriter(checkFile);

            while (readFile.hasNextLine()) { // Checks if there are any lines in our game.

                currHighScore = Integer.parseInt(readFile.nextLine());

                if (score > currHighScore) {

                    currHighScore = score;
                    writeFile.write(score);

                }

            }

            writeFile.close();
            readFile.close();

        }

        catch (IOException e) { // If there is an error finding your file then it will not crash your game.

            System.out.println("Error 101");
            e.printStackTrace();

        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) { // If the keycode right arrow is equal to the key you pressed then do something

            rightKey = false;

        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) { // If the keycode left arrow is equal to the key you pressed then do something

            leftKey = false;

        }

        if (e.getKeyCode() == KeyEvent.VK_UP) { // If the keycode down arrow is equal to the key you pressed then do something

            upKey = false;
            thruster.stop();

        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) { // If the keycode down arrow is equal to the key you pressed then do something

            spaceKey = false;

        }

        if (e.getKeyCode() == KeyEvent.VK_R) { // If the keycode down arrow is equal to the key you pressed then do something

            rKey = false;

        }

        if (e.getKeyCode() == KeyEvent.VK_N) { // If the keycode down arrow is equal to the key you pressed then do something

            nKey = false;

        }

        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) { // If the keycode down arrow is equal to the key you pressed then do something

            backSpace = false;

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) { // Anything in here will run 50 times per second.

        keyCheck();

        respawnShip(); // Checks if we need to respawn.
        ship.updatePosition(); // Updates the position of the ship based on the timer.

        for (int i = 0; i < asteroidList.size(); i++) { // Tells each asteroid to update its position.

            asteroidList.get(i).updatePosition();

        }

        for (int i = 0; i < bulletList.size(); i++) { // Tells each bullet to update its position.

            bulletList.get(i).updatePosition();

            if(bulletList.get(i).counter == 50 || bulletList.get(i).active == false) { // If 90 ticks pass, then get rid of the bullet. Our framerate is 50 times per second

                bulletList.remove(i);

            }

        }

        for (int i = 0; i < debrisList.size(); i++) {

            debrisList.get(i).updatePosition();

            if(debrisList.get(i).counter == 50 || debrisList.get(i).active == false) { // The debris lasts for 50 ticks, the dissapears

                debrisList.remove(i);

            }
        }



        checkCollision(); // Checks the collision between the asteroid and the ship.

        checkAsteroidDestruction(); // Checks if the asteroid is destroyed and removes it
    }
}
