package backend.academy.FractalFlame.transformation;

import backend.academy.FractalFlame.image.Point;
import backend.academy.FractalFlame.transformation.nonlinear.DiamondTransformation;
import backend.academy.FractalFlame.transformation.nonlinear.DiskTransformation;
import backend.academy.FractalFlame.transformation.nonlinear.HeartTransformation;
import backend.academy.FractalFlame.transformation.nonlinear.HyperbolicTransformation;
import backend.academy.FractalFlame.transformation.nonlinear.PolarTransformation;
import backend.academy.FractalFlame.transformation.nonlinear.SinusoidalTransformation;
import backend.academy.FractalFlame.transformation.nonlinear.SphericalTransformation;
import backend.academy.FractalFlame.transformation.nonlinear.SpiralTransformation;
import backend.academy.FractalFlame.transformation.nonlinear.TTransformation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

@SuppressWarnings("ReturnCount")
@UtilityClass
public class Factory {
    public static Transformation createTransformation(TransformationType type) {
        switch (type) {
            case POLAR:
                return new PolarTransformation();
            case SINUSOIDAL:
                return new SinusoidalTransformation();
            case HEART:
                return new HeartTransformation();
            case SPHERICAL:
                return new SphericalTransformation();
            case SPIRAL:
                return new SpiralTransformation();
            case DIAMOND:
                return new DiamondTransformation();
            case DISK:
                return new DiskTransformation();
            case HYPERBOLIC:
                return new HyperbolicTransformation();
            case T:
                return new TTransformation(loadPointsFromFile("/Users/Egor/Desktop/points.txt"));
            default:
                System.err.println("Неверный тип трансформации: " + type);
                return null;
        }
    }

    private static List<Point> loadPointsFromFile(String filePath) {
        List<Point> points = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath),
            StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] coords = line.trim().split("\\s+");
                if (coords.length < 2) {
                    System.err.println("Неверный формат строки: " + line);
                    continue;
                }
                try {
                    double x = Double.parseDouble(coords[0].replace(',', '.'));
                    double y = Double.parseDouble(coords[1].replace(',', '.'));
                    points.add(new Point(x, y));
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка преобразования: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return points;
    }
}
