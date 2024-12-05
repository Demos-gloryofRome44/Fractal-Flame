package backend.academy;

import backend.academy.config.Config;
import backend.academy.image.FractalImage;
import backend.academy.image.ImageFormat;
import backend.academy.image.Rect;
import backend.academy.input.InputConfigure;
import backend.academy.transformation.Transformation;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@UtilityClass
public class Main {
    public static void main(String[] args) {
        log.info("Program started");
        InputConfigure input = new InputConfigure();

        int threadsNumber = input.readInteger("Введите кол-во потоков: ");
        int width = input.readInteger("Введите ширину изображения: ");
        int height = input.readInteger("Введите высоту изображения: ");
        int pointNumber = input.readInteger("Кол-во точек: ");
        int iterations = input.readInteger("Введите количество итераций для генерации фрактала: ");
        int symmetry = input.readInteger("Введите значение симметрии: ");
        int affinesNumber = input.readInteger("Введите количество аффинных трансформаций: ");

        List<Transformation> transformations = input.readTransformations();
        List<Color> colors = input.readColors();

        ImageFormat imageFormat = input.readImageFormat("Доступные форматы изображения:");

        Rect imageRect = new Rect(-width / 2.0, -height / 2.0, width, height);

        Config config = new Config(1, pointNumber, iterations, symmetry,affinesNumber,
            imageRect, colors, transformations, imageFormat);


        log.info("Параметры заданны: {}", config);


        FractalFlameGenerator generator = new FractalFlameGenerator();

        // однопоточная версия
        Path outputPath = getFilePath("output_single", imageFormat.name().toLowerCase());
        long startTimeSingle = System.currentTimeMillis();
        generatorService(generator, config, outputPath, startTimeSingle);

        config.threadsNumber(threadsNumber); // Ставим заданное кол-во потоков
        log.info("Количество потоков установлено на: {}", threadsNumber);

        // Рендеринг многопоточной версии
        outputPath = getFilePath("output_multi", imageFormat.name().toLowerCase());
        long startTimeMulti = System.currentTimeMillis();
        generatorService(generator, config, outputPath, startTimeMulti);
        log.info("Program completed.");

    }

    private static void generatorService(FractalFlameGenerator generator,
        Config config,
        Path outputPath,
        long startTime) {
        try {
            FractalImage fractalImage = generator.generateFractalFlame(config, outputPath);
            long endTime = System.currentTimeMillis();
            String mode = config.threadsNumber() > 1 ? "многопоточный" : "однопоточный";
            log.info("{} фрактал сгенерирован за {} мс.", mode, (endTime - startTime));
        } catch (IOException | InterruptedException e) {
            log.error("Ошибка во время генерации фрактала", e);
        }
    }

    private static Path getFilePath(String baseName, String extension) {
        Path path = Paths.get("src/main/resources/" + baseName + "." + extension);

        // Проверяем наличие файла и добавляем суффикс, если файл существует
        int counter = 1;
        while (Files.exists(path)) {
            path = Paths.get("src/main/resources/" + baseName + "_" + counter + "." + extension);
            counter++;
        }

        return path;
    }
}
