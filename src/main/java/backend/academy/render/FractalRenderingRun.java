package backend.academy.render;

import backend.academy.image.FractalImage;
import backend.academy.image.Pixel;
import backend.academy.image.Point;
import backend.academy.image.Rect;
import backend.academy.transformation.Transformation;
import backend.academy.transformation.linear.AffineTransformation;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * Этот класс реализует интерфейс Runnable и выполняет генерацию изображения фрактального пламени
 * с использованием заданных аффинных и нелинейных трансформаций.
 * Каждое следующее преобрахование будет вносить свои «мазки» на картину,
 * а также изменять вклад предыдущих.
 */
@Log4j2
@AllArgsConstructor
@Getter
public class FractalRenderingRun implements Runnable {
    private static final double X_MIN = -1.777;
    private static final double X_MAX = 1.777;
    private static final double Y_MIN = -1.0;
    private static final double Y_MAX = 1.0;

    //Первые 20 итераций точку не рисуем, т.к. сначала надо найти начальную
    private static final int STEP_SKIP = 20;

    private final FractalImage resultImage;
    private final Rect world;
    private final List<AffineTransformation> affineTransformations;
    private final List<Transformation> nonlinearTransformations;
    private final int points;
    private final int iterations;
    private final int symmetry;
    private final boolean multipleThreads;

    @Override
    public void run() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Rect uiRect = new Rect(X_MIN, Y_MIN, X_MAX - X_MIN, Y_MAX - Y_MIN);

        for (int num = 0; num < points; num++) {
            double newX = random.nextDouble(X_MIN, X_MAX);
            double newY = random.nextDouble(Y_MIN, Y_MAX);
            Point point = new Point(newX, newY);

            for (int step = 0; step < iterations; step++) {
                AffineTransformation affineTransformation = affineTransformations.get(
                    random.nextInt(affineTransformations.size())
                );
                Transformation chosenVariation = nonlinearTransformations.get(
                    random.nextInt(nonlinearTransformations.size()));

                point = affineTransformation.apply(point);
                point = chosenVariation.apply(point);

                if (step <= STEP_SKIP || !uiRect.contains(point)) {
                    continue;
                }

                Point rotatedPoint;
                double angle = 0.0;

                for (int s = 0; s < symmetry; s++, angle += Math.PI * 2 / symmetry) {
                    rotatedPoint = Point.rotate(point, angle);
                    int x1 = (int) world.width()
                        - (int) (world.width() * ((X_MAX - rotatedPoint.x()) / (X_MAX - X_MIN)));
                    int y1 = (int) world.height()
                        - (int) (world.height() * ((Y_MAX - rotatedPoint.y()) / (Y_MAX - Y_MIN)));

                    if (!resultImage.contains(x1, y1)) {
                        continue;
                    }

                    Pixel pixel = resultImage.getPixel(x1, y1);

                    if (multipleThreads) {
                        synchronized (pixel) {
                            resultImage.setPixel(x1, y1, changePixelColor(pixel, affineTransformation).hit());
                        }
                    } else {
                        resultImage.setPixel(x1, y1, changePixelColor(pixel, affineTransformation).hit());
                    }
                }
            }
        }
    }

    private Pixel changePixelColor(Pixel pixel, AffineTransformation affineTransformation) {
        if (pixel.hitCount() == 0) {
            return pixel.setColor(
                affineTransformation.red(),
                affineTransformation.green(),
                affineTransformation.blue()
            );
        }

        return pixel.setColor(
            (pixel.red() + affineTransformation.red()) / 2,
            (pixel.green() + affineTransformation.green()) / 2,
            (pixel.blue() + affineTransformation.blue()) / 2
        );
    }
}
