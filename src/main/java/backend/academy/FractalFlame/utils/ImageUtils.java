package backend.academy.FractalFlame.utils;

import backend.academy.FractalFlame.image.FractalImage;
import backend.academy.FractalFlame.image.ImageFormat;
import backend.academy.FractalFlame.image.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ImageUtils {
    public static void save(FractalImage image, Path filename, ImageFormat format) {
        BufferedImage bufferedImage = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                Pixel pixel = image.getPixel(x, y);
                Color color = new Color(pixel.red(), pixel.green(), pixel.blue());
                bufferedImage.setRGB(x, y, color.getRGB());
            }
        }

        try {
            ImageIO.write(bufferedImage, format.name().toLowerCase(), filename.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении изображения: " + e.getMessage(), e);
        }
    }
}
