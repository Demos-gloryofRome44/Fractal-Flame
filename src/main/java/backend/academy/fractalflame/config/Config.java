package backend.academy.fractalflame.config;

import backend.academy.fractalflame.image.ImageFormat;
import backend.academy.fractalflame.image.Rect;
import backend.academy.fractalflame.transformation.Transformation;
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
