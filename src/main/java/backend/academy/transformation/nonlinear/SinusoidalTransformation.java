package backend.academy.transformation.nonlinear;

import backend.academy.image.Point;
import backend.academy.transformation.Transformation;

public class SinusoidalTransformation implements Transformation {
    @Override
    public Point apply(Point point) {

        double newX = Math.sin(point.x());
        double newY = Math.sin(point.y());

        return new Point(newX, newY);
    }
}
