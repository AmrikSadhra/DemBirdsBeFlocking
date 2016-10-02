import javax.swing.*;
import java.awt.*;

public class BirdHandler {

    public static void main(String[] args) {
        Color backgroundColour = Color.blue;//Initialises background colour as blue */
        Integer numBirds = getDesiredNumBirds(); //Spawns window that retrieves desired number of birds
        ColourPanel myColourChooser = new ColourPanel(); //Spawns ColourPanel that retrieves desired canvas background

        /* Polls (rather inefficiently) to see if the colour has been selected
            Done is set to 1 within the ColourPanel when the button action listener runs
         */
        while (myColourChooser.done != 1) {
            backgroundColour = myColourChooser.getColour();
            Utils.pause(1000);
        }

        Canvas mycanvas = new Canvas(800, 800, backgroundColour);
        Flock birdFlock = new Flock(mycanvas, numBirds);

        birdFlock.Fly();


    }

    /* Ensures that number is within specific range, and purely a number */
    private static int getDesiredNumBirds() {
        String errorMessage = "";
        do {
            // Show input dialog with current error message, if any
            String stringInput = JOptionPane.showInputDialog(errorMessage + "Enter desired number of birds (<50)", "25");
            try {
                int number = Integer.parseInt(stringInput);
                if (number > 50 || number < 1) {
                    errorMessage = "That number is not within the \n" + "allowed range!\n";
                } else {
                    errorMessage = ""; // no more error
                    return number;
                }
            } catch (NumberFormatException e) {
                // The typed text was not an integer
                errorMessage = "The text you typed is not a number.\n";
            }
        } while (!errorMessage.isEmpty());

        return 0;
    }
}
