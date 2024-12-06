package backend.academy.render;

import backend.academy.config.Config;
import backend.academy.image.FractalImage;
import backend.academy.transformation.linear.AffineTransformation;
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

        task.run();

        return canvas;
    }
}
