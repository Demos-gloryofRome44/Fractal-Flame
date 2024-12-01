package backend.academy.transformation.nonlinear;

import backend.academy.image.Point;
import backend.academy.transformation.Transformation;

public class HeartTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double radius = radius(point);
        double arctg = Math.atan2(point.y(), point.x());

        double newX = radius * Math.sin(radius * arctg);
        double newY = radius * -Math.cos(radius * arctg);

        return new Point(newX, newY);
    }
}
