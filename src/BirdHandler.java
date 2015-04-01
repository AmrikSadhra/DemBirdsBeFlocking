public class BirdHandler
{
    private int numBirds;
    public static void main(String [] args)
    {
        Canvas mycanvas = new Canvas(800,800);
        Flock birdFlock = new Flock(mycanvas,4);

        birdFlock.Fly(mycanvas);
    }
}

