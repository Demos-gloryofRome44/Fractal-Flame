package backend.academy.FractalFlame.transformation.nonlinear;

import backend.academy.FractalFlame.image.Point;
import backend.academy.FractalFlame.transformation.Transformation;
import java.util.List;
import java.util.Random;

public class TTransformation implements Transformation {

    private final List<Point> points;

    public TTransformation(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point apply(Point point) {
        if (points.isEmpty()) {
            return point;
        }
        Random random = new Random();

        Point randomPoint = points.get(random.nextInt(points.size()));

        // Применяем преобразование (например, просто возвращаем случайную точку)
        return new Point(randomPoint.x(), randomPoint.y());
    }
}
