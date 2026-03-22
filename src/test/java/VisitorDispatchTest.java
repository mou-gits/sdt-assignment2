import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import workflow.model.*;
import workflow.iterator.*;
import workflow.visitor.*;
import workflow.editor.*;


public class VisitorDispatchTest {

    static class RecordingVisitor implements Visitor {
        String called;

        public void visit(DelayStep step) {
            called = "delay";
        }

        public void visit(NotifyStep step) {
            called = "notify";
        }

        public void visit(TransformStep step) {
            called = "transform";
        }

        public void visit(FilterStep step) {
            called = "filter";
        }

        public void visit(CompositeStep step) {
            called = "composite";
        }
    }

    @Test
    void acceptDispatchesToCorrectVisitMethod() {
        Step step = new DelayStep("D1", 100);
        RecordingVisitor visitor = new RecordingVisitor();

        step.accept(visitor);

        assertEquals("delay", visitor.called);
    }
}