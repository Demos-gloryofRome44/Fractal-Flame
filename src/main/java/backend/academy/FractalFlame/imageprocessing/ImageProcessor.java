package backend.academy.FractalFlame.imageprocessing;

import backend.academy.FractalFlame.image.FractalImage;

@FunctionalInterface
public interface ImageProcessor {
    void process(FractalImage image);
}
