package VisitorPatternTests;

import org.junit.jupiter.api.Test;
import workflow.model.*;
import workflow.visitor.ValidationVisitor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationCompositeTest {

    @Test
    void validationTraversesCompositeChildren() {
        DelayStep bad = new DelayStep("", -5);
        CompositeStep root = new CompositeStep("Root", List.of(bad));

        ValidationVisitor visitor = new ValidationVisitor();
        root.accept(visitor);

        assertFalse(visitor.getErrors().isEmpty());
    }
}
