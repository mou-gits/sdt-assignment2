import org.junit.jupiter.api.Test;
import workflow.model.*;
import workflow.visitor.CostVisitor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class CostVisitorTest {

    @Test
    void costVisitorReturnsCorrectTotal() {
        Step s1 = new TransformStep("TrimName", "name", "trim");
        Step s2 = new DelayStep("Delay250", 250);
        Step s3 = new NotifyStep("NotifyAdmin", "done");

        CompositeStep root = new CompositeStep("Root", List.of(s1, s2, s3));

        CostVisitor visitor = new CostVisitor();
        root.accept(visitor);

        assertTrue(visitor.getTotalCost() > 0);
    }
}