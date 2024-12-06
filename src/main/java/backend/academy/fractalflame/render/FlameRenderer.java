package backend.academy.fractalflame.render;

import backend.academy.fractalflame.config.Config;
import backend.academy.fractalflame.image.FractalImage;
import backend.academy.fractalflame.transformation.linear.AffineTransformation;
import java.util.List;

public interface FlameRenderer {

    /**
     * Выполняет отрисовку фрактала на заданном прямоугольнике изображения.
     *
     * @param canvas прямоугольник, на котором будет выполняться отрисовка
     * @param config содержит параметры фрактала
     * @param affine список аффинных трансформаций, которые будут применены к фракталу
     * @return обновленный холст с отрендеренным фракталом
     */
    FractalImage render(
        FractalImage canvas,
        Config config,
        List<AffineTransformation> affine
    );
}
