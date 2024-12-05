package backend.academy.transformation.nonlinear;

import backend.academy.image.Point;
import backend.academy.transformation.Transformation;

public class PolarTransformation implements Transformation {
    public Point apply(Point point) {
        double arctg = Math.atan2(point.y(), point.x());

        double newX = arctg / Math.PI;
        double newY = radius(point) - 1;

        return new Point(newX, newY);
    }
}
