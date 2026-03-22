package IntegrationTests;

import org.junit.jupiter.api.Test;
import workflow.factory.StepFactory;
import workflow.iterator.DepthFirstIterator;
import workflow.model.Step;
import workflow.visitor.*;

        import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class WorkflowIntegrationTest {

    @Test
    void fullWorkflowIntegration() {
        Map<String, Object> config = Map.of(
                "type", "composite",
                "name", "Root",
                "steps", List.of(
                        Map.of("type", "transform", "name", "T1", "field", "f", "op", "trim"),
                        Map.of("type", "delay", "name", "D1", "ms", 200)
                )
        );

        StepFactory factory = new StepFactory();
        Step root = factory.create(config);

        // Pretty print
        PrettyPrintVisitor pp = new PrettyPrintVisitor();
        root.accept(pp);
        assertTrue(pp.getOutput().contains("Root"));

        // Cost
        CostVisitor cost = new CostVisitor();
        root.accept(cost);
        assertEquals(1 + 2, cost.getTotalCost()); // transform=1, delay=ceil(200/100)=2

        // Validation
        ValidationVisitor val = new ValidationVisitor();
        root.accept(val);
        assertTrue(val.getErrors().isEmpty());

        // DFS iterator
        DepthFirstIterator dfs = new DepthFirstIterator(root);
        assertTrue(dfs.hasNext());
    }
}
