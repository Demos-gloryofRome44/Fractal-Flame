package backend.academy.transformation.nonlinear;

import backend.academy.image.Point;
import backend.academy.transformation.Transformation;

public class DiamondTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double radius = radius(point);
        double arctg = Math.atan2(point.y(), point.x());

        double newX = Math.sin(arctg) * Math.cos(radius);
        double newY = Math.cos(arctg) * Math.sin(radius);

        return new Point(newX, newY);
    }
}
