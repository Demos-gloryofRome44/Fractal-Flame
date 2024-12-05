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
import java.nio.file.Path;
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

        long startTimeSingle = System.currentTimeMillis();
        try {
            FractalImage singleThreadImage = generator.generateFractalFlame(config, Path.of(
                "output_single." + imageFormat.name().toLowerCase()));
            long endTimeSingle = System.currentTimeMillis();
            log.info("Однопоточный фрактал сгенерирован за {} мс.", (endTimeSingle - startTimeSingle));
        } catch (IOException | InterruptedException e) {
            log.error("Ошибка во время генерации однопоточного фрактала", e);
        }

        config.threadsNumber(threadsNumber); // Ставим заданное кол-во потоков
        log.info("Количество потоков установлено на: {}", threadsNumber);

        // Рендеринг многопоточной версии
        long startTimeMulti = System.currentTimeMillis();
        try {
            FractalImage multiThreadImage = generator.generateFractalFlame(config, Path.of(
                "output_multi." + imageFormat.name().toLowerCase()));
            long endTimeMulti = System.currentTimeMillis();
            log.info("Многопоточный фрактал сгенерирован за {} мс.", (endTimeMulti - startTimeMulti));
        } catch (IOException | InterruptedException e) {
            log.error("Ошибка во время генерации многопоточного фрактала", e);
        }

        log.info("Program completed.");

    }
}
