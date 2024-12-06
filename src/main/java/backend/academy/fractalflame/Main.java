package backend.academy.fractalflame;

import backend.academy.fractalflame.config.Config;
import backend.academy.fractalflame.image.FractalImage;
import backend.academy.fractalflame.image.ImageFormat;
import backend.academy.fractalflame.image.Rect;
import backend.academy.fractalflame.input.InputConfigure;
import backend.academy.fractalflame.transformation.Transformation;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class Main {
    private static final String OUT_BASE = "src/main/resources/";

    public static void main(String[] args) {
        log.info("Program started");
        InputConfigure input = new InputConfigure();

        int threadsNumber = input.readInteger("Введите кол-во потоков: ", System.out);
        int width = input.readInteger("Введите ширину изображения: ", System.out);
        int height = input.readInteger("Введите высоту изображения: ", System.out);
        int pointNumber = input.readInteger("Кол-во точек: ", System.out);
        int iterations = input.readInteger("Введите количество итераций для генерации фрактала: ", System.out);
        int symmetry = input.readInteger("Введите значение симметрии: ", System.out);
        int affinesNumber = input.readInteger("Введите количество аффинных трансформаций: ", System.out);

        List<Transformation> transformations = input.readTransformations(System.out);
        List<Color> colors = input.readColors(System.out);

        String correctionType = input.readString("Напишите \"log\", для устанвки "
            + "логорифмической коррекции, или пропустите: ", System.out);

        ImageFormat imageFormat = input.readImageFormat("Доступные форматы изображения:", System.out);

        Rect imageRect = new Rect(-width / 2.0, -height / 2.0, width, height);

        Config config = new Config(1, pointNumber, iterations, symmetry, affinesNumber,
            imageRect, colors, transformations, imageFormat);

        log.info("Параметры заданны: {}", config);

        FractalFlameGenerator generator = new FractalFlameGenerator();

        // однопоточная версия
        Path outputPath = getFilePath("output_single", imageFormat.name().toLowerCase());
        long startTimeSingle = System.currentTimeMillis();
        generatorService(generator, config, outputPath, correctionType, startTimeSingle);

        config.threadsNumber(threadsNumber); // Ставим заданное кол-во потоков
        log.info("Количество потоков установлено на: {}", threadsNumber);

        // Рендеринг многопоточной версии
        outputPath = getFilePath("output_multi", imageFormat.name().toLowerCase());
        long startTimeMulti = System.currentTimeMillis();
        generatorService(generator, config, outputPath, correctionType, startTimeMulti);
        log.info("Program completed.");

    }

    private static void generatorService(FractalFlameGenerator generator,
        Config config,
        Path outputPath,
        String correctionType,
        long startTime) {
        try {
            FractalImage fractalImage = generator.generateFractalFlame(config, outputPath, correctionType);
            long endTime = System.currentTimeMillis();
            String mode = config.threadsNumber() > 1 ? "многопоточный" : "однопоточный";
            log.info("{} фрактал сгенерирован за {} мс.", mode, (endTime - startTime));
        } catch (IOException | InterruptedException e) {
            log.error("Ошибка во время генерации фрактала", e);
        }
    }

    private static Path getFilePath(String baseName, String extension) {
        Path path = Paths.get(OUT_BASE + baseName + "." + extension);

        int counter = 1;
        while (Files.exists(path)) {
            path = Paths.get(OUT_BASE + baseName + "_" + counter + "." + extension);
            counter++;
        }

        return path;
    }
}
