package FactoryPatternTests;

import org.junit.jupiter.api.Test;
import workflow.factory.StepFactory;
import workflow.model.CompositeStep;
import workflow.model.Step;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class NestedCompositeFactoryTest {

    @Test
    void createsNestedComposite() {
        StepFactory factory = new StepFactory();

        Map<String, Object> inner = Map.of(
                "type", "delay",
                "name", "InnerDelay",
                "ms", 100
        );

        Map<String, Object> outer = Map.of(
                "type", "composite",
                "name", "Outer",
                "steps", List.of(inner)
        );

        Step step = factory.create(outer);

        assertTrue(step instanceof CompositeStep);
        CompositeStep composite = (CompositeStep) step;

        assertEquals(1, composite.getChildren().size());
        assertEquals("InnerDelay", composite.getChildren().get(0).getName());
    }
}
