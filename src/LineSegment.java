public class LineSegment {
    private CartesianCoordinate startPoint, endPoint;

    public LineSegment(CartesianCoordinate start, CartesianCoordinate end) {
        this.startPoint = start;
        this.endPoint = end;
    }

    public CartesianCoordinate getStartPoint() {
        return this.startPoint;
    }

    public CartesianCoordinate getEndPoint() {
        return this.endPoint;
    }


}
