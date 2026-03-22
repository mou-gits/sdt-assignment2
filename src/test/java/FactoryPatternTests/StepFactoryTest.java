package FactoryPatternTests;

import org.junit.jupiter.api.Test;
import workflow.factory.StepFactory;
import workflow.model.CompositeStep;
import workflow.model.DelayStep;
import workflow.model.Step;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class StepFactoryTest {

    @Test
    void createsDelayStepFromConfig() {
        StepFactory factory = new StepFactory();

        Map<String, Object> config = new HashMap<>();
        config.put("type", "delay");
        config.put("name", "Delay500");
        config.put("ms", 500);

        Step step = factory.create(config);

        assertTrue(step instanceof DelayStep);
        assertEquals("Delay500", step.getName());
    }

    @Test
    void throwsForUnknownType() {
        StepFactory factory = new StepFactory();

        Map<String, Object> config = new HashMap<>();
        config.put("type", "unknown");
        config.put("name", "BadStep");

        assertThrows(IllegalArgumentException.class, () -> factory.create(config));
    }

    @Test
    void createsCompositeStepFromConfig() {
        StepFactory factory = new StepFactory();

        Map<String, Object> child1 = new HashMap<>();
        child1.put("type", "delay");
        child1.put("name", "Delay100");
        child1.put("ms", 100);

        Map<String, Object> child2 = new HashMap<>();
        child2.put("type", "notify");
        child2.put("name", "NotifyAdmin");
        child2.put("message", "done");

        Map<String, Object> composite = new HashMap<>();
        composite.put("type", "composite");
        composite.put("name", "MainFlow");
        composite.put("steps", List.of(child1, child2));

        Step step = factory.create(composite);

        assertTrue(step instanceof CompositeStep);
        assertEquals("MainFlow", step.getName());
    }
}