package backend.academy.fractalflame.imageprocessing;

import backend.academy.fractalflame.image.FractalImage;

@FunctionalInterface
public interface ImageProcessor {
    void process(FractalImage image);
}
