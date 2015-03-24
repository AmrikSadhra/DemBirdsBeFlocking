import java.util.Random;

public class Utils {
    public static void pause(int time) {
        try{
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // Pass silently
        }
    }

    public static int genRandom(int min, int max){
        Random generator = new Random();

        int finalRand = generator.nextInt(max-min) + min;

        return finalRand;
    }
}
