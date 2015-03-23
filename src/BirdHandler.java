public class BirdHandler
{
    public static void main(String [] args)
    {
        Canvas mycanvas = new Canvas(800,600);
        Flock birdFlock = new Flock(mycanvas, 150);

        birdFlock.Fly(mycanvas);
    }
}

