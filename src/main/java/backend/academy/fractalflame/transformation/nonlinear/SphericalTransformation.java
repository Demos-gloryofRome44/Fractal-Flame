package backend.academy.fractalflame.transformation.nonlinear;

import backend.academy.fractalflame.image.Point;
import backend.academy.fractalflame.transformation.Transformation;

public class SphericalTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double radiusSquare = radius(point) * radius(point);

        double newX = point.x() / radiusSquare;
        double newY = point.y() / radiusSquare;

        return new Point(newX, newY);
    }
}
