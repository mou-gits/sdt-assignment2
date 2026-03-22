package IntegrationTests;

import org.junit.jupiter.api.Test;
import workflow.factory.StepFactory;
import workflow.iterator.DepthFirstIterator;
import workflow.model.Step;
import workflow.visitor.PrettyPrintVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PrettyPrintAndDFSIntegrationTest {

    @Test
    void prettyPrintMatchesDFSOrder() {
        Map<String, Object> config = Map.of(
                "type", "composite",
                "name", "Root",
                "steps", List.of(
                        Map.of("type", "delay", "name", "A", "ms", 100),
                        Map.of("type", "delay", "name", "B", "ms", 200)
                )
        );

        Step root = new StepFactory().create(config);

        // DFS order
        DepthFirstIterator dfs = new DepthFirstIterator(root);
        List<String> dfsNames = new ArrayList<>();
        while (dfs.hasNext()) dfsNames.add(dfs.next().getName());

        // Pretty print
        PrettyPrintVisitor pp = new PrettyPrintVisitor();
        root.accept(pp);
        String output = pp.getOutput();

        // Ensure each DFS name appears in pretty print output
        for (String name : dfsNames) {
            assertTrue(output.contains(name));
        }
    }
}
