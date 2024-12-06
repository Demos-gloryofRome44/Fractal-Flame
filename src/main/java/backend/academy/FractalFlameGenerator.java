package backend.academy;

import backend.academy.config.Config;
import backend.academy.image.FractalImage;
import backend.academy.image.ImageFormat;
import backend.academy.imageprocessing.GammaCorrection;
import backend.academy.render.FlameRenderer;
import backend.academy.render.MultiThread;
import backend.academy.render.SingleThread;
import backend.academy.transformation.linear.AffineTransformation;
import backend.academy.utils.ImageUtils;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;


@Log4j2
public final class FractalFlameGenerator {
    private static final double GAMMA_KOEF = 2.2;

    /**
     * Генерирует фрактальное пламя с переданными параметрами генерирует линейные трансформации
     * и сохраняет результат в файл.
     *
     * @param config конфигурация генерации фракталов
     * @param filename путь к файлу для сохранения изображения
     *
     * @return сгенерированное изображение фрактала
     * @throws IOException если произошла ошибка ввода-вывода
     * @throws InterruptedException если поток был прерван
     */
    public FractalImage generateFractalFlame(
        Config config,
        Path filename) throws IOException, InterruptedException {

        FlameRenderer renderer = createRenderer(config.threadsNumber());

        // Генерация аффинных трансформаций
        List<AffineTransformation> affineTransformations = generateAffineTransformations(
            config.affineCount(), config.colors());

        FractalImage resultImage = renderer.render(
            FractalImage.create((int) config.imageRect().width(), (int) config.imageRect().height()),
            config,
            affineTransformations
        );

        applyGammaCorrection(resultImage, GAMMA_KOEF);

        saveImage(resultImage, filename, config.imageFormat());

        return resultImage;
    }

    private static FlameRenderer createRenderer(int threadsNumber) {
        return threadsNumber == 1 ? new SingleThread() : new MultiThread(threadsNumber);
    }

    private static void applyGammaCorrection(FractalImage image, double gamma) {
        GammaCorrection correction = new GammaCorrection(gamma);
        correction.process(image);
        log.info("Applied gamma correction with value: {}", gamma);
    }

    private static void saveImage(FractalImage image, Path filename, ImageFormat format) throws IOException {
        ImageUtils.save(image, filename, format);
        log.info("Image saved to {}", filename);
    }

    public List<AffineTransformation> generateAffineTransformations(int n, List<Color> colors) {
        List<AffineTransformation> transformations = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            AffineTransformation transformation = AffineTransformation.createRandomTransformation(colors);
            transformations.add(transformation);
        }

        return transformations;
    }
}
