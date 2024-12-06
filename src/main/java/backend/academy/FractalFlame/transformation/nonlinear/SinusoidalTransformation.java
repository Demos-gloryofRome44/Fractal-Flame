package backend.academy.FractalFlame.transformation.nonlinear;

import backend.academy.FractalFlame.image.Point;
import backend.academy.FractalFlame.transformation.Transformation;

public class SinusoidalTransformation implements Transformation {
    @Override
    public Point apply(Point point) {

        double newX = Math.sin(point.x());
        double newY = Math.sin(point.y());

        return new Point(newX, newY);
    }
}
