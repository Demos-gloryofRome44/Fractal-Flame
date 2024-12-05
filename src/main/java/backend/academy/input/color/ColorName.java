package backend.academy.input.color;

import lombok.ToString;
import java.awt.Color;

@ToString
public enum ColorName {
    RED(255, 0, 0),
    GREEN(0, 255, 0),
    BLUE(0, 0, 255),
    WHITE(255, 255, 255),
    BLACK(0, 0, 0),
    PINK(199, 21, 133),
    PURPLE(128, 0, 128),
    YELLOW(255, 255, 0);


    private final int r;
    private final int g;
    private final int b;

    ColorName(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color getColor() {
        return new Color(r, g, b);
    }
}
