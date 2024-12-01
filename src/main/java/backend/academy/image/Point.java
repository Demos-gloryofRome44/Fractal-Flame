package backend.academy.image;

public record Point(double x, double y) {
    public static Point rotate(Point point, double angle) {
        double radians = Math.toRadians(angle);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        double newX = point.x() * cos - point.y() * sin;
        double newY = point.x() * sin + point.y() * cos;

        return new Point(newX, newY);
    }
}
