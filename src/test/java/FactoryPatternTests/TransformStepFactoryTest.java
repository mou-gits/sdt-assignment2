package FactoryPatternTests;

import org.junit.jupiter.api.Test;
import workflow.factory.StepFactory;
import workflow.model.TransformStep;
import workflow.model.Step;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TransformStepFactoryTest {

    @Test
    void createsTransformStep() {
        StepFactory factory = new StepFactory();

        Map<String, Object> config = Map.of(
                "type", "transform",
                "name", "TrimName",
                "field", "name",
                "op", "trim"
        );

        Step step = factory.create(config);

        assertTrue(step instanceof TransformStep);
        assertEquals("TrimName", step.getName());
    }
}
