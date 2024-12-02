package backend.academy.render;

import backend.academy.Config;
import backend.academy.image.FractalImage;
import backend.academy.image.Rect;
import backend.academy.transformation.linear.AffineTransformation;
import backend.academy.transformation.Transformation;
import java.util.List;

public class SingleThread implements FlameRenderer {

    @Override
    public FractalImage render(FractalImage canvas, Config config,
        List<AffineTransformation> affine) {
        // Создание задачи рендеринга
        FractalRenderingRun task = new FractalRenderingRun(
            canvas,
            config.imageRect(),
            affine,
            config.nonLinearTransformation(),
            config.points(),
            config.iterations(),
            config.symmetry(),
            false
        );

        // Запуск рендеринга
        task.run();

        return canvas; // Возвращаем результат
    }
}
