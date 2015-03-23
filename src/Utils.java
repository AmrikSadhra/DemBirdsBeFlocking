public class Utils {
    public static void pause(int time) {
        try{
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // Pass silently
        }
    }

    public static int genRandom(int min, int max){
        int finalRand;

       finalRand = (int)Math.floor(Math.random() * (max - min)) + min;
        return finalRand;
    }
}
