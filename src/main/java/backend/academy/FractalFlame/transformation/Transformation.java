package backend.academy.FractalFlame.transformation;

import backend.academy.FractalFlame.image.Point;
import java.util.function.Function;

public interface Transformation extends Function<Point, Point> {
    default double radius(Point point) {
        return Math.sqrt(point.x() * point.x() + point.y() * point.y());
    }
}
