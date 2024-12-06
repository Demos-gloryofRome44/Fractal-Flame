package fractalflame.linearTransformation;

import backend.academy.fractalflame.image.Point;
import backend.academy.fractalflame.transformation.Factory;
import backend.academy.fractalflame.transformation.Transformation;
import backend.academy.fractalflame.transformation.TransformationType;
import backend.academy.fractalflame.transformation.nonlinear.DiamondTransformation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TransformationsTest {

    @Test
    void testOrigin() {
        DiamondTransformation transformation = new DiamondTransformation();
        Point input = new Point(0, 0);
        Point expected = new Point(0, 0);
        assertEquals(expected, transformation.apply(input));
    }

    // проверяем все трансформации на недопустимость значений
    @ParameterizedTest
    @EnumSource(TransformationType.class)
    void testTransformation(TransformationType type) {
        Transformation transformation = Factory.createTransformation(type);
        if (transformation != null) {
            Point input = new Point(1, 1);
            Point result1 = transformation.apply(input);
            Point result2 = transformation.apply(input);

            // не должно быть NaN и бесконечность
            assertFalse(Double.isNaN(result1.x()));
            assertFalse(Double.isInfinite(result1.x()));
            assertFalse(Double.isNaN(result1.y()));
            assertFalse(Double.isInfinite(result1.y()));

            if (type != TransformationType.T) {
                assertEquals(result1, result2);
            }
        }
    }
}
