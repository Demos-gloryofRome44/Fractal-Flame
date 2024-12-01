package backend.academy.transformation.linear;

import backend.academy.image.Point;
import backend.academy.transformation.Transformation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.Color;
import java.util.List;
import java.util.Random;

@Getter
@AllArgsConstructor
public class AffineTransformation implements Transformation {
    private static final double LINEAR_TRANSFORMATION_MIN = -1.0;
    private static final double LINEAR_TRANSFORMATION_MAX = 1.0;

    private final double a; // Коэффициент для x
    private final double b; // Коэффициент для y
    private final double c; // Смещение по x
    private final double d; // Коэффициент для x
    private final double e; // Коэффициент для y
    private final double f; // Смещение по y

    private final int red;
    private final int green;
    private final int blue;

    public static AffineTransformation createRandomTransformation(List<Color> colors) {
        Random random = new Random();
        double a, b, d, e;

        while (true) {
            a = random.nextDouble(LINEAR_TRANSFORMATION_MIN, LINEAR_TRANSFORMATION_MAX);
            b = random.nextDouble(LINEAR_TRANSFORMATION_MIN, LINEAR_TRANSFORMATION_MAX);
            d = random.nextDouble(LINEAR_TRANSFORMATION_MIN, LINEAR_TRANSFORMATION_MAX);
            e = random.nextDouble(LINEAR_TRANSFORMATION_MIN, LINEAR_TRANSFORMATION_MAX);

            if (isCoefficientValid(a, b, d, e)) {
                break; // Выход из цикла, если коэффициенты валидны
            }
        }

        double c = random.nextDouble(LINEAR_TRANSFORMATION_MIN, LINEAR_TRANSFORMATION_MAX);
        double f = random.nextDouble(LINEAR_TRANSFORMATION_MIN, LINEAR_TRANSFORMATION_MAX);

        Color randomColor = colors.get(random.nextInt(colors.size()));

        return new AffineTransformation(a, b, c, d, e, f, randomColor.getRed(), randomColor.getGreen(), randomColor.getBlue());
    }

    @Override
    public Point apply(Point point) {
        if (point == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        double newX = a * point.x() + b * point.y() + c;
        double newY = d * point.x() + e * point.y() + f;
        return new Point(newX, newY);
    }

    private static boolean isCoefficientValid(double a, double b, double d, double e) {
        return a * a + d * d < 1
            && b * b + e * e < 1
            && a * b + d * e < 1 + (a * e + b * d) * (a * e + b * d);
    }
}
