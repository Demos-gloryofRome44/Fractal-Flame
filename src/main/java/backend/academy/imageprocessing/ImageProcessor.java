package backend.academy.imageprocessing;

import backend.academy.image.FractalImage;

@FunctionalInterface
public interface ImageProcessor {
    void process(FractalImage image);
}
