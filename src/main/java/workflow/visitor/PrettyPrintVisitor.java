package workflow.visitor;

import workflow.model.*;

public class PrettyPrintVisitor implements Visitor {

    private int depth = 0;
    private StringBuilder output = new StringBuilder();

    public String getOutput() {
        return output.toString();
    }

    private void appendIndent() {
        for (int i = 0; i < depth; i++) {
            output.append("  ");
        }
    }

    @Override
    public void visit(CompositeStep step) {
        appendIndent();
        output.append("CompositeStep: ")
                .append(step.getName())
                .append("\n");
        depth++;
    }

    @Override
    public void leaveComposite(CompositeStep step) {
        depth--;
    }

    @Override
    public void visit(DelayStep step) {
        appendIndent();
        output.append("DelayStep: ")
                .append(step.getName())
                .append("\n");
    }

    @Override
    public void visit(NotifyStep step) {
        appendIndent();
        output.append("NotifyStep: ")
                .append(step.getName())
                .append("\n");
    }

    @Override
    public void visit(TransformStep step) {
        appendIndent();
        output.append("TransformStep: ")
                .append(step.getName())
                .append("\n");
    }

    @Override
    public void visit(FilterStep step) {
        appendIndent();
        output.append("FilterStep: ")
                .append(step.getName())
                .append("\n");
    }
}
