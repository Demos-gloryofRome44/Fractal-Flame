package backend.academy.FractalFlame.transformation.nonlinear;

import backend.academy.FractalFlame.image.Point;
import backend.academy.FractalFlame.transformation.Transformation;

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
