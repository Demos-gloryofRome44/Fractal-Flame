package fractalflame.generator;

import backend.academy.fractalflame.FractalFlameGenerator;
import backend.academy.fractalflame.config.Config;
import backend.academy.fractalflame.image.ImageFormat;
import backend.academy.fractalflame.image.Rect;
import backend.academy.fractalflame.transformation.Transformation;
import backend.academy.fractalflame.transformation.nonlinear.HeartTransformation;
import backend.academy.fractalflame.transformation.nonlinear.PolarTransformation;
import backend.academy.fractalflame.transformation.nonlinear.SinusoidalTransformation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FractalGeneratorTest {

    // Создаем временный каталог для тестовых файлов
    @TempDir
    Path tempDir;

    private Config createConfig(int pointNumber, int iterations, List<Transformation> transformations, List<Color> colors) {
        Rect imageRect = new Rect(-960.0, -540.0, 1920.0, 1080.0);
        return Config.builder()
            .threadsNumber(1)
            .points(pointNumber)
            .iterations(iterations)
            .symmetry(1)
            .affineCount(10)
            .imageRect(imageRect)
            .colors(colors)
            .nonLinearTransformation(transformations)
            .imageFormat(ImageFormat.PNG)
            .build();
    }

    // Тест на создание изображния фрактального пламени
    // Сделана проверка того что файл создан и не является пустым изображнием
    @Test
    void testFractalGeneration() throws IOException, InterruptedException {
        FractalFlameGenerator generator = new FractalFlameGenerator();
        List<Color> colors = new ArrayList<>();
        colors.add(new Color(255, 0, 0));
        colors.add(new Color(0, 255, 0));
        List<Transformation> transformations = new ArrayList<>();
        transformations.add(new HeartTransformation());

        Config config = createConfig(1000, 1000, transformations, colors);

        Path outputPath = tempDir.resolve("output_test.png");
        generator.generateFractalFlame(config, outputPath, "");

        assertTrue(Files.exists(outputPath), "Файл с фракталом не был создан.");

        // Проверка размера файла, что он содержит какое то изображние
        assertTrue(Files.size(outputPath) > 0, "Файл с фракталом пустой.");

        // Удаляем временный файл после теста
        Files.delete(outputPath);
    }

    // Тест на допустимость нескольних нелинейных трансформаций, не мешающей программе
    @Test
    void testFractalGeneration_DifferentTransformations() throws IOException, InterruptedException {
        FractalFlameGenerator generator = new FractalFlameGenerator();
        List<Color> colors = new ArrayList<>();
        colors.add(new Color(255, 0, 0));
        colors.add(new Color(0, 255, 0));

        List<Transformation> transformations = new ArrayList<>();
        transformations.add(new PolarTransformation());
        transformations.add(new SinusoidalTransformation());

        Config config = createConfig(1000, 1000, transformations, colors);

        Path outputPath = tempDir.resolve("output_test2.png");
        generator.generateFractalFlame(config, outputPath, "");

        assertTrue(Files.exists(outputPath), "Файл с фракталом не был создан.");
        assertTrue(Files.size(outputPath) > 0);

        Files.delete(outputPath);
    }

    // подадим разные параметры для проверки генерации изображения при разных параметрах
    @Test
    void testFractalGeneration_DifferentParameters() throws IOException, InterruptedException {
        FractalFlameGenerator generator = new FractalFlameGenerator();

        List<Color> colors = new ArrayList<>();
        colors.add(new Color(255, 0, 0));
        colors.add(new Color(0, 255, 0));

        List<Transformation> transformations = new ArrayList<>();
        transformations.add(new HeartTransformation());

        Config config = createConfig(5000, 500, transformations, colors); // Пример

        Path outputPath = tempDir.resolve("output_test3.png");
        generator.generateFractalFlame(config, outputPath, "");

        assertTrue(Files.exists(outputPath), "Файл с фракталом не был создан.");
        assertTrue(Files.size(outputPath) > 0);

        Files.delete(outputPath);
    }

    @Test
    void testFractalGeneration_EmptyColors() {
        FractalFlameGenerator generator = new FractalFlameGenerator();

        List<Color> colors = new ArrayList<>();
        List<Transformation> transformations = new ArrayList<>();
        transformations.add(new HeartTransformation());

        // Проверяем выброс исключения при передаче пустого списка цветов
        assertThrows(IllegalArgumentException.class, () -> {
            Config config = createConfig(1000, 1000, transformations, colors);
            Path outputPath = tempDir.resolve("output_test_empty_colors.png");
            generator.generateFractalFlame(config, outputPath,"");
        });
    }

    @Test
    void testFractalGeneration_EmptyTransformations() {
        FractalFlameGenerator generator = new FractalFlameGenerator();

        List<Color> colors = new ArrayList<>();
        colors.add(new Color(255, 0, 0));
        List<Transformation> transformations = new ArrayList<>();

        // Проверяем выброс исключения при подаче пустого списка нелинейных трансформаций
        assertThrows(IllegalArgumentException.class, () -> {
            Config config = createConfig( 1000, 1000, transformations, colors);
            Path outputPath = tempDir.resolve("output_test_empty_transformations.png");
            generator.generateFractalFlame(config, outputPath,"");
        });
    }
}
