package backend.academy.fractalflame.transformation.nonlinear;

import backend.academy.fractalflame.image.Point;
import backend.academy.fractalflame.transformation.Transformation;

public class PolarTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double arctg = Math.atan2(point.y(), point.x());

        double newX = arctg / Math.PI;
        double newY = radius(point) - 1;

        return new Point(newX, newY);
    }
}
