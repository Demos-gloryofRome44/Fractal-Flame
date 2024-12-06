package backend.academy.input;

import backend.academy.image.ImageFormat;
import backend.academy.input.color.ColorName;
import backend.academy.transformation.Factory;
import backend.academy.transformation.Transformation;
import backend.academy.transformation.TransformationType;
import java.awt.Color;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputConfigure {
    private static final String STOP_MESSAGE = "Введите 'stop' для завершения.";
    private static final String STOP = "stop";
    private final Scanner scanner;

    public InputConfigure() {
        this.scanner = new Scanner(System.in);
    }

    public int readInteger(String prompt, PrintStream out) {
        while (true) {
            out.print(prompt);
            String input = scanner.nextLine();
            try {
                int value = Integer.parseInt(input);
                if (value <= 0) {
                    out.println("Ошибка: введите положительное число.");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                out.println("Ошибка: введите целое число.");
            }
        }
    }

    public String readString(String prompt, PrintStream out) {
        out.print(prompt);
        return scanner.nextLine();
    }

    public List<Transformation> readTransformations(PrintStream out) {
        List<Transformation> transformations = new ArrayList<>();
        String input = "";

        out.println("Доступные типы трансформаций: ");
        for (TransformationType type : TransformationType.values()) {
            out.println("- " + type.name());
        }

        out.println(STOP_MESSAGE);

        while (!input.equalsIgnoreCase(STOP)) {
            input = readString("Введите тип трансформации: ", System.out);

            if (input.equalsIgnoreCase(STOP)) {
                break;
            }

            TransformationType type = getTransformationType(input.toUpperCase());
            if (type != null) {
                Transformation transformation = Factory.createTransformation(type);
                transformations.add(transformation);
            } else {
                out.println("Неверный ввод. Пожалуйста, попробуйте снова.");
            }
        }
        return transformations;
    }

    public List<Color> readColors(PrintStream out) {
        List<Color> colors = new ArrayList<>();
        String input;

        out.println("Доступные типы цветов:");

        for (ColorName color : ColorName.values()) {
            out.println("- " + color.name());
        }

        out.println(STOP_MESSAGE);

        while (true) {
            input = readString("Введите цвет: ", System.out);
            if (input.equalsIgnoreCase(STOP)) {
                break;
            }

            ColorName colorName = getColorType(input);
            if (colorName != null) {
                colors.add(colorName.getColor());
            } else {
                out.println("Ошибка: неверное имя цвета. Пожалуйста, попробуйте снова.");
            }
        }

        return colors;
    }

    public ImageFormat readImageFormat(String prompt, PrintStream out) {
        out.print(prompt);
        List<ImageFormat> formats = Arrays.asList(ImageFormat.values());


        for (ImageFormat format : formats) {
            out.print(" - " + format);
        }

        String input;
        while (true) {
            out.print("\nВведите формат: ");
            input = scanner.nextLine().toUpperCase();
            String finalInput = input;
            if (Arrays.stream(ImageFormat.values()).anyMatch(format -> format.name().equals(finalInput))) {
                return ImageFormat.valueOf(input);
            } else {
                out.println("Ошибка: неверный формат. Пожалуйста, выберите один из следующих: " + formats);
            }
        }
    }

    private TransformationType getTransformationType(String input) {
        for (TransformationType type : TransformationType.values()) {
            if (type.name().equalsIgnoreCase(input)) {
                return type;
            }
        }
        return null;
    }

    private ColorName getColorType(String input) {
        for (ColorName type : ColorName.values()) {
            if (type.name().equalsIgnoreCase(input)) {
                return type;
            }
        }
        return null;
    }
}
