/**
 * ************************************************************************
 * <p/>
 * CARTESIAN DOUBLE
 * Class to store x and y coordinate pair as doubles.
 * <p/>
 * *************************************************************************
 */
public class CartesianDouble {
    private double xcoord, ycoord;

    /**
     * ************************************************************************
     * CONSTRUCTOR
     * *************************************************************************
     */
    public CartesianDouble(double xValue, double yValue) {
        this.xcoord = xValue;
        this.ycoord = yValue;
    }

    /**
     * ************************************************************************
     * SET X
     * Set the x coordinate
     * *************************************************************************
     */
    public void setX(double newX) {
        this.xcoord = newX;
    }

    /**
     * ************************************************************************
     * SET Y
     * Set the Y coordinate
     * *************************************************************************
     */
    public void setY(double newY) {
        this.ycoord = newY;
    }

    /**
     * ************************************************************************
     * GET X
     * Return the x coordinate
     * *************************************************************************
     */
    public double getX() {
        return this.xcoord;
    }

    /**
     * ************************************************************************
     * GET Y
     * Return the y coordinate
     * *************************************************************************
     */
    public double getY() {
        return this.ycoord;
    }

    /**
     * ************************************************************************
     * GET CARTESIAN
     * Returns a CartesianCoordinate object with integer x and y values
     * *************************************************************************
     */
    public CartesianCoordinate getCartesian() {
        return new CartesianCoordinate((int) this.xcoord, (int) this.ycoord);
    }
}