package backend.academy.transformation.nonlinear;

import backend.academy.image.Point;
import backend.academy.transformation.Transformation;

public class SphericalTransformation implements Transformation {
    @Override
    public Point apply(Point point) {

        double newX = point.x()/ radius(point) * radius(point);
        double newY = point.y()/ radius(point) * radius(point);;

        return new Point(newX, newY);
    }
}
