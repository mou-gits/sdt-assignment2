import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import workflow.model.*;
import workflow.iterator.*;
import workflow.visitor.*;
import workflow.editor.*;


public class ValidationVisitorTest {

    @Test
    void validationVisitorCatchesErrors() {
        DelayStep badStep = new DelayStep("", -100);

        ValidationVisitor visitor = new ValidationVisitor();
        badStep.accept(visitor);

        assertFalse(visitor.getErrors().isEmpty());
    }

    @Test
    void validationVisitorAcceptsValidStep() {
        DelayStep goodStep = new DelayStep("Delay100", 100);

        ValidationVisitor visitor = new ValidationVisitor();
        goodStep.accept(visitor);

        assertTrue(visitor.getErrors().isEmpty());
    }
}