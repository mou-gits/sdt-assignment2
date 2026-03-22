package IntegrationTests;

import org.junit.jupiter.api.Test;
import workflow.factory.StepFactory;
import workflow.model.Step;
import workflow.visitor.ValidationVisitor;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InvalidWorkflowTest {

    @Test
    void invalidWorkflowProducesErrors() {
        Map<String, Object> config = Map.of(
                "type", "composite",
                "name", "BadRoot",
                "steps", List.of(
                        Map.of("type", "delay", "name", "", "ms", -10)
                )
        );

        Step root = new StepFactory().create(config);

        ValidationVisitor val = new ValidationVisitor();
        root.accept(val);

        assertFalse(val.getErrors().isEmpty());
    }
}
