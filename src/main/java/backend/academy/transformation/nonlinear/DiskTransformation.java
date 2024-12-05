package backend.academy.transformation.nonlinear;

import backend.academy.image.Point;
import backend.academy.transformation.Transformation;

public class DiskTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double radius = radius(point);
        double arctg = Math.atan2(point.y(), point.x());
        double pi = Math.PI;

        double newX = (arctg * Math.sin(radius * pi)) / pi;
        double newY = (arctg * Math.cos(radius * pi)) / pi;

        return new Point(newX, newY);
    }
}
