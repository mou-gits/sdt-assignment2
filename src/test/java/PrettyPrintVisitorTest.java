import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import workflow.model.*;
import workflow.iterator.*;
import workflow.visitor.*;
import workflow.editor.*;

public class PrettyPrintVisitorTest {

    @Test
    void prettyPrintContainsExpectedStructure() {
        Step s1 = new TransformStep("TrimName", "name", "trim");
        Step s2 = new DelayStep("Delay500", 500);
        CompositeStep root = new CompositeStep("Workflow", List.of(s1, s2));

        PrettyPrintVisitor visitor = new PrettyPrintVisitor();
        root.accept(visitor);

        String output = visitor.getOutput();

        assertTrue(output.contains("Workflow"));
        assertTrue(output.contains("TrimName"));
        assertTrue(output.contains("Delay500"));
    }
}