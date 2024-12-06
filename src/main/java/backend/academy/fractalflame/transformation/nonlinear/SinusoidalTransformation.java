package backend.academy.fractalflame.transformation.nonlinear;

import backend.academy.fractalflame.image.Point;
import backend.academy.fractalflame.transformation.Transformation;

public class SinusoidalTransformation implements Transformation {

    @Override
    public Point apply(Point point) {

        double newX = Math.sin(point.x());
        double newY = Math.sin(point.y());

        return new Point(newX, newY);
    }
}
