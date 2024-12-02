package backend.academy.imageprocessing;

import backend.academy.image.FractalImage;
import backend.academy.image.Pixel;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GammaCorrection implements ImageProcessor{
    private final double gamma;
    private static final int MAX_RGB = 255;

    public GammaCorrection(double gamma) {
        this.gamma = gamma;
    }

    public void process(FractalImage image) {
        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                Pixel pixel = image.getPixel(x, y);

                if (pixel == null) {
                    log.warn("Pixel at ({}, {}) is null", x, y);
                    continue;
                }

                int correctedRed = (int) (MAX_RGB * Math.pow(pixel.red() / (double) MAX_RGB, gamma));
                int correctedGreen = (int) (MAX_RGB * Math.pow(pixel.green() / (double) MAX_RGB, gamma));
                int correctedBlue = (int) (MAX_RGB * Math.pow(pixel.blue() / (double) MAX_RGB, gamma));

                pixel = pixel.setColor(correctedRed, correctedGreen, correctedBlue);
                image.setPixel(x, y, pixel);
            }
        }
    }
}
