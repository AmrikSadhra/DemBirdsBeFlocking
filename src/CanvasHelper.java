/**
 * Created by amriksadhra on 02/05/15.
 *
 * Needed so that drawCursor and drawBird didn't interfere with one another. Private
 * instances of this class solved issues.
 */
public class CanvasHelper {
    private Canvas myCanvas;

    public CanvasHelper(Canvas canvas) {
        this.myCanvas = canvas;
    }

    public CartesianDouble calculateEndPoint(CartesianDouble startPosition, int distance, int bearing) {
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

    public void drawLine(CartesianDouble startPoint, CartesianDouble endPoint, String colour) {
        this.myCanvas.drawLineBetweenPoints(startPoint.getCartesian(), endPoint.getCartesian(), colour);
    }
}
