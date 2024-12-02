package backend.academy;

import backend.academy.image.FractalImage;
import backend.academy.image.ImageFormat;
import backend.academy.image.Rect;
import backend.academy.imageprocessing.GammaCorrection;
import backend.academy.imageprocessing.LogGammaCorrection;
import backend.academy.render.MultiThread;
import backend.academy.transformation.linear.AffineTransformation;
import backend.academy.render.FlameRenderer;
import backend.academy.render.SingleThread;
import backend.academy.transformation.Transformation;
import backend.academy.utils.ImageUtils;
import lombok.extern.log4j.Log4j2;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public final class FractalFlameGenerator {
    public FractalImage generateFractalFlame(
        Config config,
        Path filename) throws IOException, InterruptedException {

        validateParameters(config.threadsNumber(), config.symmetry());

        FlameRenderer renderer = createRenderer(config.threadsNumber());

        // Генерация аффинных трансформаций
        List<AffineTransformation> affineTransformations = generateAffineTransformations(
            config.affineCount(), config.colors());

        FractalImage resultImage = renderer.render(
            FractalImage.create((int) config.imageRect().width(), (int) config.imageRect().height()),
            imageRect,
            affineTransformations,
            nonLinearTransformation,
            pointsNumber,
            iterationNumber,
            symmetry
        );

        applyGammaCorrection(resultImage, 2.2);

        saveImage(resultImage, filename);

        return resultImage;
    }


    private static void validateParameters(int threadsNumber, int symmetry) {
        if (threadsNumber <= 0) {
            throw new IllegalArgumentException("Number of threads must be positive");
        }
        if (symmetry <= 0) {
            throw new IllegalArgumentException("Symmetry must be positive");
        }
    }

    private static FlameRenderer createRenderer(int threadsNumber) {
        return threadsNumber == 1 ? new SingleThread() : new MultiThread();
    }

    private static void applyGammaCorrection(FractalImage image, double gamma) {
        GammaCorrection correction = new GammaCorrection(gamma);
        correction.process(image);
        log.info("Applied gamma correction with value: {}", gamma);
    }

    private static void saveImage(FractalImage image, Path filename) throws IOException {
        ImageUtils.save(image, filename, ImageFormat.PNG);
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
