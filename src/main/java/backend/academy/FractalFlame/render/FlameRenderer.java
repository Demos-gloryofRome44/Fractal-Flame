package backend.academy.FractalFlame.render;

import backend.academy.FractalFlame.config.Config;
import backend.academy.FractalFlame.image.FractalImage;
import backend.academy.FractalFlame.transformation.linear.AffineTransformation;
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
