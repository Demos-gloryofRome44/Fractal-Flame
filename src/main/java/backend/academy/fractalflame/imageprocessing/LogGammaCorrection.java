package backend.academy.fractalflame.imageprocessing;

import backend.academy.fractalflame.image.FractalImage;
import backend.academy.fractalflame.image.Pixel;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogGammaCorrection implements ImageProcessor {
    private final double gamma;
    private static final int MAX_RGB = 255;

    public LogGammaCorrection(double gamma) {
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

                double correctedRed = Math.log(1 + pixel.red()) * (MAX_RGB / Math.log(MAX_RGB));
                double correctedGreen = Math.log(1 + pixel.green()) * (MAX_RGB / Math.log(MAX_RGB));
                double correctedBlue = Math.log(1 + pixel.blue()) * (MAX_RGB / Math.log(MAX_RGB));

                correctedRed = Math.pow(correctedRed / MAX_RGB, gamma) * MAX_RGB;
                correctedGreen = Math.pow(correctedGreen / MAX_RGB, gamma) * MAX_RGB;
                correctedBlue = Math.pow(correctedBlue / MAX_RGB, gamma) * MAX_RGB;

                int finalRed = (int) Math.min(MAX_RGB, Math.max(0, correctedRed));
                int finalGreen = (int) Math.min(MAX_RGB, Math.max(0, correctedGreen));
                int finalBlue = (int) Math.min(MAX_RGB, Math.max(0, correctedBlue));

                pixel = pixel.setColor(finalRed, finalGreen, finalBlue);
                image.setPixel(x, y, pixel);
            }
        }
    }
}
