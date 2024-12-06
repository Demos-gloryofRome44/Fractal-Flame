package backend.academy.FractalFlame.transformation.nonlinear;

import backend.academy.FractalFlame.image.Point;
import backend.academy.FractalFlame.transformation.Transformation;

public class PolarTransformation implements Transformation {
    public Point apply(Point point) {
        double arctg = Math.atan2(point.y(), point.x());

        double newX = arctg / Math.PI;
        double newY = radius(point) - 1;

        return new Point(newX, newY);
    }
}
