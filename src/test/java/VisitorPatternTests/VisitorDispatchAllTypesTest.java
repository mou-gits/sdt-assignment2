package VisitorPatternTests;

import org.junit.jupiter.api.Test;
import workflow.model.*;
import workflow.visitor.Visitor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitorDispatchAllTypesTest {

    static class Recorder implements Visitor {
        String called = "";

        public void visit(DelayStep s) { called = "delay"; }
        public void visit(NotifyStep s) { called = "notify"; }
        public void visit(TransformStep s) { called = "transform"; }
        public void visit(FilterStep s) { called = "filter"; }
        public void visit(CompositeStep s) { called = "composite"; }
        public void leaveComposite(CompositeStep s) {}
    }

    @Test
    void dispatchesCorrectlyForAllTypes() {
        Recorder r = new Recorder();

        new DelayStep("A", 1).accept(r);
        assertEquals("delay", r.called);

        new NotifyStep("B", "x").accept(r);
        assertEquals("notify", r.called);

        new TransformStep("C", "f", "op").accept(r);
        assertEquals("transform", r.called);

        new FilterStep("D", "f", "x").accept(r);
        assertEquals("filter", r.called);

        new CompositeStep("E", List.of()).accept(r);
        assertEquals("composite", r.called);
    }
}
