package backend.academy.fractalflame.render;

import backend.academy.fractalflame.config.Config;
import backend.academy.fractalflame.image.FractalImage;
import backend.academy.fractalflame.transformation.linear.AffineTransformation;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThread implements FlameRenderer {
    private static final int TIMEOUT = 5; // ожидание потока

    private final ExecutorService executorService;

    public MultiThread(int threadCount) {
        this.executorService = Executors.newFixedThreadPool(threadCount);
    }

    @Override
    public FractalImage render(FractalImage canvas, Config config,
        List<AffineTransformation> affine) {
        config.points(config.points() / config.threadsNumber());

        for (int i = 0; i < config.threadsNumber(); ++i) {
            FractalRenderingRun task = new FractalRenderingRun(
                canvas,
                config.imageRect(),
                affine,
                config.nonLinearTransformation(),
                config.points(),
                config.iterations(),
                config.symmetry(),
                true
            );
            executorService.submit(task);
        }
        executorService.shutdown();

        try {
            executorService.awaitTermination(TIMEOUT, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return canvas;
    }
}
