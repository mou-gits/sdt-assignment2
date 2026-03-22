package workflow.visitor;

import workflow.model.*;

public class PrettyPrintVisitor implements Visitor {

    private int depth = 0;
    private StringBuilder output = new StringBuilder();

    public String getOutput() {
        return output.toString();
    }

    private void printIndent() {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
            output.append("  ");
        }
    }

    @Override
    public void visit(CompositeStep step) {
        printIndent();
        System.out.println("CompositeStep: " + step.getName());
        output.append("CompositeStep: ").append(step.getName()).append("\n");
        depth++;
    }

    @Override
    public void leaveComposite(CompositeStep step) {
        depth--;
    }

    @Override
    public void visit(DelayStep step) {
        printIndent();
        System.out.println("DelayStep: " + step.getName());
        output.append("DelayStep: ").append(step.getName()).append("\n");
    }

    @Override
    public void visit(NotifyStep step) {
        printIndent();
        System.out.println("NotifyStep: " + step.getName());
        output.append("NotifyStep: ").append(step.getName()).append("\n");
    }

    @Override
    public void visit(TransformStep step) {
        printIndent();
        System.out.println("TransformStep: " + step.getName());
        output.append("TransformStep: ").append(step.getName()).append("\n");
    }

    @Override
    public void visit(FilterStep step) {
        printIndent();
        System.out.println("FilterStep: " + step.getName());
        output.append("FilterStep: ").append(step.getName()).append("\n");
    }
}