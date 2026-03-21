package workflow.visitor;

import workflow.model.*;

public class PrettyPrintVisitor implements Visitor {

    private int depth = 0;

    private void printIndent() {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
    }

    @Override
    public void visit(DelayStep step) {
        printIndent();
        System.out.println("DelayStep: " + step.getName());
    }

    @Override
    public void visit(NotifyStep step) {
        printIndent();
        System.out.println("NotifyStep: " + step.getName());
    }

    @Override
    public void visit(TransformStep step) {
        printIndent();
        System.out.println("TransformStep: " + step.getName());
    }

    @Override
    public void visit(FilterStep step) {
        printIndent();
        System.out.println("FilterStep: " + step.getName());
    }

    @Override
    public void visit(CompositeStep step) {
        printIndent();
        System.out.println("CompositeStep: " + step.getName());
        depth++;
    }
}