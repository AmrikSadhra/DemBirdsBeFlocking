import java.awt.*;

public class Bird {
    //Constants for code readbility
    public static final int COHESION = 0, SEPARATION = 1, ALIGNMENT = 2, SEARCHING = 3;

    //Instance Variables
    private int currentAngle, birdNumber, birdState; //Current angle that Bird is facing (in degrees),
    private static int pixelsToMove, i, birdCounter;
    private boolean inFlock = false;
    public CartesianDouble currentPosition; // current x,y coordinate
    private Canvas canvas; // private instance variable reference to a canvas object
    private Point mouseCoords;
    private String colour;

    //Constructor
    public Bird(Canvas canvas) {
        birdNumber = birdCounter;
        birdCounter++;
        this.canvas = canvas;
        this.currentPosition = new CartesianDouble(Utils.genRandom(20, canvas.getWidth() - 20), Utils.genRandom(20, canvas.getHeight()-20));
        //this.currentPosition = new CartesianDouble(Utils.genRandom(380, 420), 400);
        //this.currentPosition = new CartesianDouble(400, Utils.genRandom(280, 320));
        //this.currentPosition = new CartesianDouble(400, 400);
        this.currentAngle = Utils.genRandom(0, 360);
        this.colour = "black";
        this.drawBird();
    }

    public void simulate(Flock flock) {
        this.move(5);
        flock.localBirds();
        int flockSize = 100;
        mouseCoords = MouseInfo.getPointerInfo().getLocation();
        continuousScroll();

        if ((flock.getNumLocalBirds(birdNumber) > flockSize) && (inFlock == true)) {
            this.birdState = SEPARATION;
            this.inFlock = false;
            this.colour = "green";
        } else if ((flock.getNumLocalBirds(birdNumber) <= flockSize) && (flock.getNumLocalBirds(birdNumber) != 0)) {
            this.birdState = ALIGNMENT;
            this.inFlock = false;
            this.colour = "blue";
        } else if ((flock.getNumLocalBirds(birdNumber) == 0) && (this.inFlock == false)) {
            this.birdState = SEARCHING;
            this.colour = "red";
        } //else if (Just_Right) this.intelliTurn(flock, COHESION);

            this.intelliTurn(flock, this.birdState);

    }

    public void intelliTurn(Flock flock, int stateOfBird) {
        int turnAngle = 5;
        if (currentAngle <= 0) currentAngle += 360;
        if (currentAngle >= 360) currentAngle -= 360;

        switch (stateOfBird) {
            case SEPARATION:
                if (currentAngle < flock.getAverageDirectionToLocalFlock(birdNumber)) {
                    this.currentAngle -= turnAngle;
                } else if (currentAngle > flock.getAverageDirectionToLocalFlock(birdNumber)) {
                    this.currentAngle += turnAngle;
                } else if (currentAngle == flock.getAverageDirectionToLocalFlock(birdNumber))
                    this.turn(Utils.genRandom(-turnAngle, turnAngle));
                break;

            case COHESION:
                break;

            case ALIGNMENT:
                if (currentAngle < flock.getAverageBirdHeading(birdNumber)) {
                    this.currentAngle += turnAngle;
                } else if (currentAngle > flock.getAverageBirdHeading(birdNumber)) {
                    this.currentAngle -= turnAngle;
                } else if (currentAngle > flock.getAverageBirdHeading(birdNumber)) {
                    this.currentAngle -= turnAngle;
                }
                break;

            case SEARCHING:
                this.turn(Utils.genRandom(-turnAngle, turnAngle));
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
        this.canvas.drawLineBetweenPoints(startPoint.getCartesian(), endPoint.getCartesian(), this.colour);
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

    private void continuousScroll() {
        //Wrap around if bird goes off screen
        if (this.currentPosition.getX() >= canvas.getWidth()) {
            this.currentPosition.setX(1);
        }
        if (this.currentPosition.getX() <= 0) {
            this.currentPosition.setX(canvas.getWidth());
        }
        if (this.currentPosition.getY() >= canvas.getHeight()) {
            this.currentPosition.setY(1);
        }
        if (this.currentPosition.getY() <= 0) {
            this.currentPosition.setY(canvas.getHeight());
        }
    }

    private void stateHandler() {
    }
}
