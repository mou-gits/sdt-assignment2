package VisitorPatternTests;

import org.junit.jupiter.api.Test;
import workflow.model.*;
import workflow.visitor.PrettyPrintVisitor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PrettyPrintIndentationTest {

    @Test
    void printsCorrectIndentationForNestedComposite() {
        CompositeStep inner = new CompositeStep("Inner", List.of(
                new DelayStep("D1", 100)
        ));

        CompositeStep root = new CompositeStep("Root", List.of(inner));

        PrettyPrintVisitor visitor = new PrettyPrintVisitor();
        root.accept(visitor);

        String output = visitor.getOutput();

        assertTrue(output.contains("CompositeStep: Root"));
        assertTrue(output.contains("  CompositeStep: Inner"));
        assertTrue(output.contains("    DelayStep: D1"));
    }
}
