package com.company;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class AudioUtil {

    private static AudioUtil ourInstance = new AudioUtil(); // Making a reference to the class.

    public static AudioUtil getInstance() { // Returns the instance of our audio.

        return ourInstance;

    }
    public AudioUtil() { // Constructor

        //e

    }

    // Checking if a file is readable, if it is not then it will THROW and error.
    //Our game does not crash if there is an error with sound now.
    public URL transform(File audioFile) throws MalformedURLException {
        if (audioFile.canRead()){ // If the file is readable then.

            return audioFile.toURI().toURL(); // Return the URL of the file so I can find it.

        }

        throw new IllegalArgumentException(); // If you can't read it then THROW an exception

    }

}
