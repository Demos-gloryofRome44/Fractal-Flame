package fractalflame.performance;

import backend.academy.fractalflame.FractalFlameGenerator;
import backend.academy.fractalflame.config.Config;
import backend.academy.fractalflame.image.ImageFormat;
import backend.academy.fractalflame.image.Rect;
import backend.academy.fractalflame.transformation.Transformation;
import backend.academy.fractalflame.transformation.nonlinear.HeartTransformation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Класс PerformanceTest предназначен для тестирования производительности
 * генерации фракталов с использованием однопоточной и многопоточной конфигураций.
 * Тест измеряет время выполнения генерации фрактала для обоих подходов
 * и сравнивает их, чтобы убедиться, что многопоточная версия быстрее.
 */
public class PerformanceTest {

    @TempDir
    Path tempDir;

    /**
     *
     * Этот тест создает экземпляр генератора фракталов и настраивает
     * параметры для однопоточной и многопоточной генерации. Нельзя достоверно
     * измерять время ведь тесты выполняются парельно и дабы не запускать каждый тест отдельно
     * мы используем пул потоков для выполнения обеих задач параллельно, что
     * предотвращает влияние времени выполнения одного теста на другой и обеспечивает
     * более надежные результаты. Время выполнения каждой конфигурации измеряется
     * и выводится на экран, выполняя проверку, что многопоточная версия
     * выполняется быстрее однопоточной.
     *
     * @throws IOException если возникает ошибка ввода-вывода при генерации фрактала.
     * @throws InterruptedException если выполнение потока прерывается.
     * @throws ExecutionException если возникает ошибка при выполнении задачи.
     */
    @Test
    void testFractalGenerationPerformance() throws IOException, InterruptedException, ExecutionException {
        FractalFlameGenerator generator = new FractalFlameGenerator();
        Rect imageRect = new Rect(-960.0, -540.0, 1920.0, 1080.0);
        List<Color> colors = new ArrayList<>();
        colors.add(new Color(255, 0, 0));
        colors.add(new Color(0, 255, 0));
        List<Transformation> transformations = new ArrayList<>();
        transformations.add(new HeartTransformation());

        // Создаем пул потоков с фиксированным количеством потоков (в данном случае 2)
        ExecutorService executor = Executors.newFixedThreadPool(2); // Создаем пул потоков

        // однопоточка
        Config configSingle = Config.builder()
            .threadsNumber(1)
            .points(1000)
            .iterations(1000)
            .symmetry(1)
            .affineCount(10)
            .imageRect(imageRect)
            .colors(colors)
            .nonLinearTransformation(transformations)
            .imageFormat(ImageFormat.PNG)
            .build();

        // многопоточка
        Config configMulti = Config.builder()
            .threadsNumber(10)
            .points(1000)
            .iterations(1000)
            .symmetry(1)
            .affineCount(10)
            .imageRect(imageRect)
            .colors(colors)
            .nonLinearTransformation(transformations)
            .imageFormat(ImageFormat.PNG)
            .build();

        // Запускаем задачу для однопоточной версии в пуле потоков
        Future<Long> futureSingle = executor.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                long startTime = System.currentTimeMillis();
                generator.generateFractalFlame(configSingle, tempDir.resolve("output_single.png"), " ");
                long endTime = System.currentTimeMillis();
                return endTime - startTime;
            }
        });

        // многопоточка в пуле потоков
        Future<Long> futureMulti = executor.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                long startTime = System.currentTimeMillis();
                generator.generateFractalFlame(configMulti, tempDir.resolve("output_multi.png"), " ");
                long endTime = System.currentTimeMillis();
                return endTime - startTime;
            }
        });

        long singleThreadDuration = futureSingle.get();
        long multiThreadDuration = futureMulti.get();

        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);


        System.out.println("Однопоточная версия: " + singleThreadDuration + " мс");
        System.out.println("Многопоточная версия: " + multiThreadDuration + " мс");

        assertTrue(multiThreadDuration < singleThreadDuration,
            "Многопоточная версия не должна быть медленнее однопоточной.");
    }
}
