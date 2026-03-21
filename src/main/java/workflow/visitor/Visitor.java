package workflow.visitor;

import workflow.model.*;

public interface Visitor {

    void visit(DelayStep step);

    void visit(NotifyStep step);

    void visit(TransformStep step);

    void visit(FilterStep step);

    void visit(CompositeStep step);
}