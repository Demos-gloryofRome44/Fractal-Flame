package backend.academy.fractalflame.transformation.nonlinear;

import backend.academy.fractalflame.image.Point;
import backend.academy.fractalflame.transformation.Transformation;

public class SpiralTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double radius = radius(point);
        double arctg = Math.atan2(point.y(), point.x());

        double newX = (Math.cos(arctg) + Math.sin(radius)) / radius;
        double newY = (Math.sin(arctg) - Math.cos(radius)) / radius;

        return new Point(newX, newY);
    }
}
