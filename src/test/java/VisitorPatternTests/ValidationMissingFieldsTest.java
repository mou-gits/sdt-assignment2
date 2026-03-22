package VisitorPatternTests;

import org.junit.jupiter.api.Test;
import workflow.model.TransformStep;
import workflow.visitor.ValidationVisitor;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationMissingFieldsTest {

    @Test
    void transformMissingFieldAndOpProducesErrors() {
        TransformStep step = new TransformStep("T1", "", "");

        ValidationVisitor visitor = new ValidationVisitor();
        step.accept(visitor);

        assertFalse(visitor.getErrors().isEmpty());
    }
}
