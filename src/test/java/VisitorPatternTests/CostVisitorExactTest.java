package VisitorPatternTests;

import org.junit.jupiter.api.Test;
import workflow.model.*;
import workflow.visitor.CostVisitor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CostVisitorExactTest {

    @Test
    void computesExactCost() {
        Step s1 = new TransformStep("T", "f", "op"); // cost 1
        Step s2 = new FilterStep("F", "f", "x");     // cost 1
        Step s3 = new NotifyStep("N", "msg");        // cost 2
        Step s4 = new DelayStep("D", 250);           // ceil(250/100)=3

        CompositeStep root = new CompositeStep("Root", List.of(s1, s2, s3, s4));

        CostVisitor visitor = new CostVisitor();
        root.accept(visitor);

        assertEquals(1 + 1 + 2 + 3, visitor.getTotalCost());
    }
}
