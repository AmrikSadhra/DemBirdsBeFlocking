/**
 * Created by amriksadhra on 02/05/15.
 */
public class Predator extends Bird {
    private Canvas currentCanvas;

    public Predator(Canvas canvas) {
        super(canvas);
        currentCanvas = canvas;
        this.colour = "red";
    }

    /* Moves predator a set number of pixels in current direction */
    public void move(int pixels) {
        CartesianDouble newPosition;

        newPosition = birdHelper.calculateEndPoint(this.currentPosition, pixels, this.currentAngle);
        this.currentPosition = newPosition;
    }

    /* Turns Predator a set number of degrees */
    public void turn(int degrees) {
        this.currentAngle = currentAngle -= degrees;
    }

    /* Wraps predator around canvas if it goes off screen */
    public void wrapAround() {
        if (this.currentPosition.getX() > currentCanvas.getWidth()) this.currentPosition.setX(1);
        if (this.currentPosition.getX() < 0) this.currentPosition.setX(currentCanvas.getWidth() - 1);
        if (this.currentPosition.getY() > currentCanvas.getHeight()) this.currentPosition.setY(1);
        if (this.currentPosition.getY() < 0) this.currentPosition.setY(currentCanvas.getHeight() - 1);
    }
}
