package backend.academy.input;

import backend.academy.ColorName;
import backend.academy.transformation.Factory;
import backend.academy.transformation.Transformation;
import backend.academy.transformation.TransformationType;
import java.awt.Color;
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
                int value = Integer.parseInt(input);
                if (value <= 0) {
                    System.out.println("Ошибка: введите положительное число.");
                } else {
                    return value;
                }
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

    public List<Color> readColors() {
        List<Color> colors = new ArrayList<>();
        String input;

        System.out.println("Доступные типы цветов:");
        for (ColorName color : ColorName.values()) {
            System.out.println("- " + color.name());
        }

        System.out.println("Введите 'stop' для завершения.");

        while (true) {
            input = readString("Введите цвет: ");
            if (input.equalsIgnoreCase("stop")) {
                break;
            }

            try {
                ColorName colorName = ColorName.valueOf(input.toUpperCase());
                colors.add(colorName.getColor());
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: неверное имя цвета. Пожалуйста, попробуйте снова.");
            }
        }

        return colors;
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
