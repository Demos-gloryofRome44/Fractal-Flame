package backend.academy;

import backend.academy.image.FractalImage;
import backend.academy.image.Rect;
import backend.academy.transformation.nonlinear.HeartTransformation;
import backend.academy.transformation.nonlinear.PolarTransformation;
import backend.academy.transformation.nonlinear.SinusoidalTransformation;
import backend.academy.transformation.Transformation;
import backend.academy.transformation.nonlinear.SphericalTransformation;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Log4j2
@UtilityClass
public class Main {
    public static void main(String[] args) {
        List<Transformation> transformations = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите кол-во потоков: ");
        int threadsNumber = Integer.parseInt(scanner.nextLine());

        // Ввод ширины изображения
        System.out.print("Введите ширину изображения: ");
        int width = Integer.parseInt(scanner.nextLine());

        // Ввод высоты изображения
        System.out.print("Введите высоту изображения: ");
        int height = Integer.parseInt(scanner.nextLine());

        System.out.print("Кол-во точек: ");
        int pointNumber = Integer.parseInt(scanner.nextLine());

        // Ввод количества итераций
        System.out.print("Введите количество итераций для генерации фрактала: ");
        int iterations = Integer.parseInt(scanner.nextLine());

        System.out.print("Введите значение симметрии: ");
        int symmetry = Integer.parseInt(scanner.nextLine());

        // Ввод количества аффинных трансформаций
        System.out.print("Введите количество аффинных трансформаций: ");
        int affinesNumber = Integer.parseInt(scanner.nextLine());

        String input = "";
        while (!input.equalsIgnoreCase("stop")){
            System.out.print("Введите тип трансформации (Heart/Polar/Sinusoidal) или 'stop' для завершения: ");
            input = scanner.nextLine();

            switch (input.toLowerCase()) {
                case "polar":
                    transformations.add(new PolarTransformation());
                    break;
                case "sinusoidal":
                    transformations.add(new SinusoidalTransformation());
                    break;
                case "heart":
                    transformations.add((new HeartTransformation()));
                case "sferical":
                    transformations.add(new SphericalTransformation());
                case "stop":
                    break;
                default:
                    System.out.println("Неверный ввод. Пожалуйста, попробуйте снова.");
            }
        }

        // Логирование введенных данных
        log.info("Image Width: {}", width);
        log.info("Image Height: {}", height);
        log.info("Iterations: {}", iterations);
        log.info("Transformations: {}", transformations);

        Rect imageRect = new Rect(-width / 2.0, -height / 2.0, width, height);
        FractalFlameGenerator generator = new FractalFlameGenerator();
        List<Color> colors = getRandomColors();

        Config config = new Config(threadsNumber, pointNumber, iterations, symmetry,affinesNumber,
            imageRect, colors, transformations);

        try {
            FractalImage resultImage = generator.generateFractalFlame(
                1,
                pointNumber,
                affinesNumber,
                iterations,
                imageRect,
                symmetry,
                colors,
                transformations,
                Path.of("output.png")
            );
        } catch (IOException | InterruptedException e) {
            log.error("Error during fractal generation", e);
        }

        scanner.close();
    }

    private static List<Color> getRandomColors() {
        // Реализуйте метод для генерации случайных цветов
        List<Color> colors = new ArrayList<>();


        colors.add(new Color(0, 0, 0));
        colors.add(new Color(255,  255, 255));
        colors.add(new Color(255, 0, 0));
        //colors.add(new Color(0, 255, 0));
        //colors.add(new Color(0, 0, 255));
        //colors.add(new Color(158, 103, 210));


        return colors;
    }
}
