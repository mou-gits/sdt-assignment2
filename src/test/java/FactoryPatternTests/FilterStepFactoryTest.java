package FactoryPatternTests;

import org.junit.jupiter.api.Test;
import workflow.factory.StepFactory;
import workflow.model.FilterStep;
import workflow.model.Step;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FilterStepFactoryTest {

    @Test
    void createsFilterStep() {
        StepFactory factory = new StepFactory();

        Map<String, Object> config = Map.of(
                "type", "filter",
                "name", "OnlyGmail",
                "field", "email",
                "contains", "@gmail.com"
        );

        Step step = factory.create(config);

        assertTrue(step instanceof FilterStep);
        assertEquals("OnlyGmail", step.getName());
    }
}
