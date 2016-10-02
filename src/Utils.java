import java.io.*;
import java.util.Random;

public class Utils {
    public static void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // Pass silently
        }
    }

    public static int genRandom(int min, int max) {
        Random generator = new Random();

        int finalRand = generator.nextInt(max - min) + min;

        return finalRand;
    }

    /* Saves slider values to text file */
    public static void FileSave(SliderPanel myPanel, File inFile) {

        // The name of the file to open.
        String fileName = inFile.getAbsolutePath() + "/DBBFConfig.txt";

        try {
            // Assume default encoding.
            FileWriter fileWriter = new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("#* DemBirdsBeFlocking Config File * #");
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(myPanel.slider1.getValue()));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(myPanel.slider2.getValue()));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(myPanel.slider3.getValue()));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(myPanel.slider4.getValue()));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(myPanel.slider5.getValue()));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(myPanel.slider6.getValue()));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(myPanel.slider7.getValue()));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(myPanel.slider8.getValue()));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(myPanel.slider9.getValue()));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(myPanel.slider10.getValue()));
            bufferedWriter.newLine();
            bufferedWriter.write(String.valueOf(myPanel.slider11.getValue()));
            bufferedWriter.newLine();

            //Close file
            bufferedWriter.close();

            myPanel.messages.append("DBBFConfig.txt saved to " + inFile.getAbsolutePath() + "\n");
        } catch (IOException ex) {
            System.out.println("Error writing to file '" + fileName + "'");
        }
    }

    /* Loads slider values from text file */
    public static void FileLoad(SliderPanel myPanel, File inFile) {

        // The name of the file to open.
        String fileName = inFile.getAbsolutePath();

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            if (!(bufferedReader.readLine().contains("#* DemBirdsBeFlocking Config File * #"))) {
                myPanel.messages.append("Invalid file. Try again.");
                return;
            }
            myPanel.slider1.setValue(Integer.parseInt(bufferedReader.readLine()));
            myPanel.slider1.updateUI();
            myPanel.slider2.setValue(Integer.parseInt(bufferedReader.readLine()));
            myPanel.slider2.updateUI();
            myPanel.slider3.setValue(Integer.parseInt(bufferedReader.readLine()));
            myPanel.slider3.updateUI();
            myPanel.slider4.setValue(Integer.parseInt(bufferedReader.readLine()));
            myPanel.slider4.updateUI();
            myPanel.slider5.setValue(Integer.parseInt(bufferedReader.readLine()));
            myPanel.slider5.updateUI();
            myPanel.slider6.setValue(Integer.parseInt(bufferedReader.readLine()));
            myPanel.slider6.updateUI();
            myPanel.slider7.setValue(Integer.parseInt(bufferedReader.readLine()));
            myPanel.slider7.updateUI();
            myPanel.slider8.setValue(Integer.parseInt(bufferedReader.readLine()));
            myPanel.slider8.updateUI();
            myPanel.slider9.setValue(Integer.parseInt(bufferedReader.readLine()));
            myPanel.slider9.updateUI();
            myPanel.slider10.setValue(Integer.parseInt(bufferedReader.readLine()));
            myPanel.slider10.updateUI();
            myPanel.slider11.setValue(Integer.parseInt(bufferedReader.readLine()));
            myPanel.slider11.updateUI();

            // Always close files.
            bufferedReader.close();
            myPanel.messages.append(inFile.getAbsolutePath() + " Loaded Successfully! " + "\n");
        } catch (FileNotFoundException ex) {
            myPanel.messages.append(
                    "Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            myPanel.messages.append(
                    "Error reading file '" + fileName + "'");
        }
    }
}

