package backend.academy.FractalFlame.config;

import backend.academy.FractalFlame.image.ImageFormat;
import backend.academy.FractalFlame.image.Rect;
import backend.academy.FractalFlame.transformation.Transformation;
import java.awt.Color;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Config {
    private int threadsNumber;
    private int points;
    private final int iterations;
    private final int symmetry;
    private final int affineCount;
    private final Rect imageRect;
    private List<Color> colors;
    private final List<Transformation> nonLinearTransformation;
    ImageFormat imageFormat;
}
