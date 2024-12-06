package backend.academy.image;

public record Point(double x, double y) {
    public static Point rotate(Point point, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        return new Point(
            cos * point.x() - sin * point.y(),
            sin * point.x() + cos * point.y()
        );
    }
}
