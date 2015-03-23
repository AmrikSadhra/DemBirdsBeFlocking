import java.awt.*;

public class Bird {
    //Constants for code readbility
    public static final int COHESION = 0, SEPARATION = 1, ALIGNMENT = 2;

    //Instance Variables
    private int currentAngle, birdNumber, speed; //Current angle that Bird is facing (in degrees),
    private static int pixelsToMove, i, birdCounter;
    private boolean penIsDown; // state of the pen, true when pen down
    public CartesianDouble currentPosition; // current x,y coordinate
    private Canvas canvas; // private instance variable reference to a canvas object
    private Point mouseCoords;

    //Constructor
    public Bird(Canvas canvas) {
        birdNumber = birdCounter;
        birdCounter++;
        this.canvas = canvas;
        //this.currentPosition = new CartesianDouble(Utils.genRandom(20, 780), Utils.genRandom(20, 580));
        this.currentPosition = new CartesianDouble(Utils.genRandom(380, 420), Utils.genRandom(280, 320));
        this.currentAngle = Utils.genRandom(0, 360);
        this.drawBird();
    }

    public void simulate(Flock flock) {
        mouseCoords = MouseInfo.getPointerInfo().getLocation();

       //Wrap around if bird goes off screen
        if (this.currentPosition.getX() >= 800) {
            this.currentPosition.setX(1);
        }
        if (this.currentPosition.getX() <= 0) {
            this.currentPosition.setX(800);
        }
        if (this.currentPosition.getY() >= 600) {
            this.currentPosition.setY(1);
        }
        if (this.currentPosition.getY() <= 0) {
            this.currentPosition.setY(600);
        }
        this.move(5);
        this.intelliTurn(flock, COHESION);
        /*
        if (Crowded)  this.intelliTurn(flock, SEPARATION);
        if (Alone)  this.intelliTurn(flock, ALIGNMENT);
        if (Just_Right) this.intelliTurn(flock, COHESION);*/
    }

    public void intelliTurn(Flock flock, int birdState) {


        switch(birdState) {
            case SEPARATION:
                this.turn(Utils.genRandom(-10, 10));
                break;

            case COHESION:
                //Limit range: wraparound
                if (currentAngle >= 360) currentAngle-= 360;
                if (currentAngle <= -360) currentAngle-= 0;
                //Turn towards average heading
                if (currentAngle < flock.getAverageBirdHeading(birdNumber)) this.currentAngle++;
                if (currentAngle > flock.getAverageBirdHeading(birdNumber)) this.currentAngle--;
                else this.turn(Utils.genRandom(-10, 10));
                break;

            case ALIGNMENT:
                break;
        }
    }

    public int getHeading() {
        return currentAngle;
    }

    public CartesianDouble getPosition() {
        return currentPosition;
    }

    public void turn(int angle) {
        this.currentAngle = this.currentAngle + angle;
    }

    public void move(int pixels) {
        CartesianDouble newPosition;

        newPosition = this.calculateEndPoint(this.currentPosition,
                pixels,
                this.currentAngle);

        this.currentPosition = newPosition;
    }

    //CALCULATE ENDPOINT METHOD: calculates a new coordinate given a start point, distance and bearing
    private CartesianDouble calculateEndPoint(CartesianDouble startPosition, int distance, int bearing) {
        CartesianDouble endPosition;

        double deltaX, deltaY; // relative distance moved x,y

        double newX, newY; // new positions after move x,y

        // calculate deltaX and deltaY
        deltaX = distance * Math.cos(Math.toRadians(bearing));
        deltaY = distance * Math.sin(Math.toRadians(bearing));

        // calculate new floating point coordinates
        newX = startPosition.getX() + deltaX;
        newY = startPosition.getY() + deltaY;

        // make new position coordinate
        return new CartesianDouble(newX, newY);
    }

    private void drawLine(CartesianDouble startPoint, CartesianDouble endPoint) {
        this.canvas.drawLineBetweenPoints(startPoint.getCartesian(), endPoint.getCartesian(), "blue");
    }

    public void drawBird() {
        int localBearing;
        CartesianDouble localPosition, nextPosition;

        // array holding angles and distances to draw a Bird
        int[][] polarArray = new int[][]{{120, -150, -120, -150},
                {10, 20, 20, 10}};

        // set local bearing to the same as the Bird
        localBearing = this.currentAngle;

        // set local position reference to current position
        localPosition = this.currentPosition;

        for (int i = 0; i < 4; i++) {
            // update local bearing
            localBearing = localBearing + polarArray[0][i];
            // calculate new position given local bearing and distance
            nextPosition = this.calculateEndPoint(localPosition, polarArray[1][i], localBearing);
            // draw a line between local position and new position
            this.drawLine(localPosition, nextPosition);
            // update local position reference to point at new position
            localPosition = nextPosition;
        }
    }

    public void undrawBird() {
        for (int i = 0; i < 4; i++) // remove 4 lines from canvas
        {
            this.canvas.removeMostRecentLine();
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
