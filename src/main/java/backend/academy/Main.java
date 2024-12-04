package backend.academy;

import backend.academy.image.FractalImage;
import backend.academy.image.Point;
import backend.academy.image.Rect;
import backend.academy.transformation.linear.AffineTransformation;
import backend.academy.transformation.nonlinear.DiamondTransformation;
import backend.academy.transformation.nonlinear.DiskTransformation;
import backend.academy.transformation.nonlinear.HeartTransformation;
import backend.academy.transformation.nonlinear.PolarTransformation;
import backend.academy.transformation.nonlinear.SinusoidalTransformation;
import backend.academy.transformation.Transformation;
import backend.academy.transformation.nonlinear.SphericalTransformation;
import backend.academy.transformation.nonlinear.SpiralTransformation;
import backend.academy.transformation.nonlinear.TTransformation;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Log4j2
@UtilityClass
public class Main {
    public static void main(String[] args) {
        InputConfigure input = new InputConfigure();

        int threadsNumber = input.readInteger("Введите кол-во потоков: ");
        int width = input.readInteger("Введите ширину изображения: ");
        int height = input.readInteger("Введите высоту изображения: ");
        int pointNumber = input.readInteger("Кол-во точек: ");
        int iterations = input.readInteger("Введите количество итераций для генерации фрактала: ");
        int symmetry = input.readInteger("Введите значение симметрии: ");
        int affinesNumber = input.readInteger("Введите количество аффинных трансформаций: ");

        List<Transformation> transformations = input.readTransformations();

        log.info("Image Width: {}", width);
        log.info("Image Height: {}", height);
        log.info("Iterations: {}", iterations);
        log.info("Transformations: {}", transformations);

        Rect imageRect = new Rect(-width / 2.0, -height / 2.0, width, height);
        FractalFlameGenerator generator = new FractalFlameGenerator();
        List<Color> colors = getRandomColors();

        Config config = new Config(1, pointNumber, iterations, symmetry,affinesNumber,
            imageRect, colors, transformations);


        long startTimeSingle = System.currentTimeMillis();
        try {
            FractalImage singleThreadImage = generator.generateFractalFlame(config, Path.of("output_single.png"));
            long endTimeSingle = System.currentTimeMillis();
            log.info("Однопоточный фрактал сгенерирован за {} мс.", (endTimeSingle - startTimeSingle));
        } catch (IOException | InterruptedException e) {
            log.error("Ошибка во время генерации однопоточного фрактала", e);
        }

        config.threadsNumber(threadsNumber); // Ставим заданное кол-во потоков

        // Рендеринг многопоточной версии
        long startTimeMulti = System.currentTimeMillis();
        try {
            FractalImage multiThreadImage = generator.generateFractalFlame(config, Path.of("output_multi.png"));
            long endTimeMulti = System.currentTimeMillis();
            log.info("Многопоточный фрактал сгенерирован за {} мс.", (endTimeMulti - startTimeMulti));
        } catch (IOException | InterruptedException e) {
            log.error("Ошибка во время генерации многопоточного фрактала", e);
        }

    }

    private static List<Color> getRandomColors() {
        List<Color> colors = new ArrayList<>();


        colors.add(new Color(0, 0, 0));
        colors.add(new Color(255,  255, 255));
        colors.add(new Color(255, 0, 0));
        //colors.add(new Color(255, 255, 0));
        //colors.add(new Color(0, 0, 255));
        //colors.add(new Color(158, 103, 210));


        return colors;
    }
}
