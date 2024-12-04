package backend.academy;

import backend.academy.image.Point;
import backend.academy.transformation.Factory;
import backend.academy.transformation.Transformation;
import backend.academy.transformation.TransformationType;
import backend.academy.transformation.nonlinear.DiamondTransformation;
import backend.academy.transformation.nonlinear.DiskTransformation;
import backend.academy.transformation.nonlinear.HeartTransformation;
import backend.academy.transformation.nonlinear.PolarTransformation;
import backend.academy.transformation.nonlinear.SinusoidalTransformation;
import backend.academy.transformation.nonlinear.SphericalTransformation;
import backend.academy.transformation.nonlinear.SpiralTransformation;
import backend.academy.transformation.nonlinear.TTransformation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputConfigure {
    private final Scanner scanner;

    public InputConfigure() {
        this.scanner = new Scanner(System.in);
    }

    public int readInteger(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            }
        }
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public List<Transformation> readTransformations() {
        List<Transformation> transformations = new ArrayList<>();
        String input = "";

        System.out.println("Доступные типы трансформаций: ");
        for (TransformationType type : TransformationType.values()) {
            System.out.println("- " + type.name());
        }
        System.out.println("Введите 'stop' для завершения.");

        while (!input.equalsIgnoreCase("stop")) {
            input = readString("Введите тип трансформации: ");

            if (!input.equalsIgnoreCase("stop")) {
                TransformationType type = getTransformationType(input.toUpperCase());
                if (type != null) {
                    Transformation transformation = Factory.createTransformation(type);
                    transformations.add(transformation);
                } else {
                    System.out.println("Неверный ввод. Пожалуйста, попробуйте снова.");
                }
            }
        }
        return transformations;
    }

    private TransformationType getTransformationType(String input) {
        for (TransformationType type : TransformationType.values()) {
            if (type.name().equalsIgnoreCase(input)) {
                return type;
            }
        }
        return null;
    }
}
