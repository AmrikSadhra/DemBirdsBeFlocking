public class Bird {

    //Instance Variables
    public int birdNumber, currentAngle;
    protected static int birdCounter;
    public CartesianDouble currentPosition, velocity; // current x,y coordinate, velocity
    public CanvasHelper birdHelper;
    private Canvas canvas; // private instance variable reference to a canvas object
    public String colour;

    //Constructor
    public Bird(Canvas canvas) {
        this.birdNumber = birdCounter;
        birdCounter++;
        this.velocity = new CartesianDouble(Utils.genRandom(-10, 10), Utils.genRandom(-10, 10));
        this.canvas = canvas;
        this.currentPosition = new CartesianDouble(Utils.genRandom(100, canvas.getWidth() - 100), Utils.genRandom(100, canvas.getHeight() - 100));
        this.currentAngle = Utils.genRandom(0, 360);
        this.colour = "black";
        birdHelper = new CanvasHelper(canvas);//Creates canvasHelper, so that drawBird can function.
    }

    /* Chris Harte's supplied turtle drawing code, wid squares. */
    public void drawBird() {
        int localBearing;
        CartesianDouble localPosition, nextPosition;

        // array holding angles and distances to draw a Bird
        int[][] polarArray = new int[][]{{-90, -90, -90, -90},
                {20, 20, 20, 20}};

        // set local bearing to the same as the Bird
        localBearing = this.currentAngle;

        // set local position reference to current position
        localPosition = this.currentPosition;

        for (int i = 0; i < 4; i++) {
            // update local bearing
            localBearing = localBearing + polarArray[0][i];
            // calculate new position given local bearing and distance
            nextPosition = this.birdHelper.calculateEndPoint(localPosition, polarArray[1][i], localBearing);
            // draw a line between local position and new position
            this.birdHelper.drawLine(localPosition, nextPosition, colour);
            // update local position reference to point at new position
            localPosition = nextPosition;
        }
    }

}
