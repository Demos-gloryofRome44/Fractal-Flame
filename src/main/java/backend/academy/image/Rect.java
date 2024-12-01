package backend.academy.image;

import backend.academy.transformation.Transformation;
import java.awt.Color;
import java.util.List;
import java.util.Random;

public record Rect(double x, double y, double width, double height) {
    public boolean contains(Point point) {
        return point.x() >= x && point.x() <= (x + width) &&
            point.y() >= y && point.y() <= (y + height);
    }
}
