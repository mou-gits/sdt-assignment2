package FactoryPatternTests;

import org.junit.jupiter.api.Test;
import workflow.factory.StepFactory;
import workflow.model.NotifyStep;
import workflow.model.Step;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class NotifyStepFactoryTest {

    @Test
    void createsNotifyStep() {
        StepFactory factory = new StepFactory();

        Map<String, Object> config = Map.of(
                "type", "notify",
                "name", "NotifyAdmin",
                "message", "done"
        );

        Step step = factory.create(config);

        assertTrue(step instanceof NotifyStep);
        assertEquals("NotifyAdmin", step.getName());
    }
}
