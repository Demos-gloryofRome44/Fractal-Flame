package backend.academy.transformation.nonlinear;

import backend.academy.image.Point;
import backend.academy.transformation.Transformation;

public class HyperbolicTransformation implements Transformation {
    public Point apply(Point point) {
        double radius = radius(point);
        double arctg = Math.atan2(point.y(), point.x());

        double newX = Math.sin(arctg) / radius;
        double newY = radius * Math.cos(arctg);

        return new Point(newX, newY);
    }
}
