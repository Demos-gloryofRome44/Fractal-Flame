package backend.academy.fractalflame.render;

import backend.academy.fractalflame.config.Config;
import backend.academy.fractalflame.image.FractalImage;
import backend.academy.fractalflame.transformation.linear.AffineTransformation;
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
