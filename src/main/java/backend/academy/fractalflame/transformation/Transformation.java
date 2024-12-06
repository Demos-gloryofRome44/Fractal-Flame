package backend.academy.fractalflame.transformation;

import backend.academy.fractalflame.image.Point;
import java.util.function.Function;

public interface Transformation extends Function<Point, Point> {
    default double radius(Point point) {
        return Math.sqrt(point.x() * point.x() + point.y() * point.y());
    }
}
