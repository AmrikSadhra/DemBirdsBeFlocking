/**
 * Created by amriksadhra on 30/04/15.
 */
public class Vectors {

    /* All functions simplify vector arithmetic.
        Functions utilise varags, arguments stored into array and accessed in for loop
        Therefore accept variable number of arguments
     */
    public static CartesianDouble add(CartesianDouble... vec) {
        double sumX = vec[0].getX(), sumY = vec[0].getY();

        for (int i = 1; i < vec.length; i++) {
            sumX += vec[i].getX();
            sumY += vec[i].getY();
        }

        return new CartesianDouble(sumX, sumY);
    }

    public static CartesianDouble subtract(CartesianDouble... vec) {
        double sumX = vec[0].getX(), sumY = vec[0].getY();


        for (int i = 1; i < vec.length; i++) {
            sumX -= vec[i].getX();
            sumY -= vec[i].getY();
        }

        return new CartesianDouble(sumX, sumY);
    }

    public static CartesianDouble multiply(CartesianDouble... vec) {
        double sumX = vec[0].getX(), sumY = vec[0].getY();

        for (int i = 1; i < vec.length; i++) {
            sumX *= vec[i].getX();
            sumY *= vec[i].getY();
        }

        return new CartesianDouble(sumX, sumY);
    }

    public static CartesianDouble divide(CartesianDouble... vec) {
        double sumX = vec[0].getX(), sumY = vec[0].getY();

        for (int i = 1; i < vec.length; i++) {
            sumX /= vec[i].getX();
            sumY /= vec[i].getY();
        }

        return new CartesianDouble(sumX, sumY);
    }

    public static CartesianDouble abs(CartesianDouble vec) {
        return new CartesianDouble(Math.abs(vec.getX()), Math.abs(vec.getY()));
    }

    public static CartesianDouble scalarMultiply(CartesianDouble vec, double number) {
        return new CartesianDouble(number * vec.getX(), number * vec.getY());
    }

    public static CartesianDouble scalarAdd(double number, CartesianDouble vec) {
        return new CartesianDouble(number + vec.getX(), number + vec.getY());
    }

    public static CartesianDouble scalarSubtract(double number, CartesianDouble vec) {
        return new CartesianDouble(number - vec.getX(), number - vec.getY());
    }

    public static CartesianDouble scalarDivide(CartesianDouble vec, double number) {
        return new CartesianDouble(vec.getX() / number, vec.getY() / number);
    }

}
