package backend.academy;

import backend.academy.image.Rect;
import backend.academy.transformation.linear.AffineTransformation;
import backend.academy.transformation.Transformation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.awt.Color;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Config {
    private int threadsNumber;
    private int points;
    private final int iterations;
    private final int symmetry;
    private final int affineCount;
    private final Rect imageRect;
    private List<Color> colors;
    private final List<Transformation> nonLinearTransformation;
}
