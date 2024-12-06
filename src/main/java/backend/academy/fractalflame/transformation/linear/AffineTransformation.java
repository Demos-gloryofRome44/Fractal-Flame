package backend.academy.fractalflame.transformation.linear;

import backend.academy.fractalflame.image.Point;
import backend.academy.fractalflame.transformation.Transformation;
import java.awt.Color;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс, представляет линейную аффинную трансформацию.
 * Этот класс реализует интерфейс Transformation и позволяет применять
 * аффинные преобразования к точкам, а также генерировать случайные
 * аффинные трансформации с заданными цветами.
 */
@Getter
@AllArgsConstructor
public class AffineTransformation implements Transformation {
    private static final double LIN_MIN = -1.0;
    private static final double LIN_MAX = 1.0;

    private final double a;
    private final double b;
    private final double c;
    private final double d;
    private final double e;
    private final double f;

    private final int red;
    private final int green;
    private final int blue;

    public static AffineTransformation createRandomTransformation(List<Color> colors) {
        Random random = new Random();
        double a;
        double b;
        double d;
        double e;

        while (true) {
            a = random.nextDouble(LIN_MIN, LIN_MAX);
            b = random.nextDouble(LIN_MIN, LIN_MAX);
            d = random.nextDouble(LIN_MIN, LIN_MAX);
            e = random.nextDouble(LIN_MIN, LIN_MAX);

            if (isCoefficientValid(a, b, d, e)) {
                break;
            }
        }

        double c = random.nextDouble(LIN_MIN, LIN_MAX);
        double f = random.nextDouble(LIN_MIN, LIN_MAX);

        Color randomColor = colors.get(random.nextInt(colors.size()));

        return new AffineTransformation(a, b, c, d, e, f,
            randomColor.getRed(), randomColor.getGreen(), randomColor.getBlue());
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
