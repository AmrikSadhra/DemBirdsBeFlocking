public class Utils {
    public static void pause(int time) {
        try{
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // Pass silently
        }
    }

    public static int genRandom(int min, int max){

        int finalRand = -min + (int) (Math.random() * ((max - (-min)) + 1));

        return finalRand;
    }
}
