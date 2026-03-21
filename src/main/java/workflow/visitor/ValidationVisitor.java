package workflow.visitor;

import workflow.model.*;

import java.util.ArrayList;
import java.util.List;

public class ValidationVisitor implements Visitor {

    private List<String> errors = new ArrayList<>();

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public void visit(DelayStep step) {
        if (step.getName() == null || step.getName().isEmpty()) {
            errors.add("DelayStep has empty name");
        }
        if (step.getMs() <= 0) {
            errors.add("DelayStep '" + step.getName() + "' has non-positive delay");
        }
    }

    @Override
    public void visit(NotifyStep step) {
        if (step.getName() == null || step.getName().isEmpty()) {
            errors.add("NotifyStep has empty name");
        }
        if (step.getMessage() == null || step.getMessage().isEmpty()) {
            errors.add("NotifyStep '" + step.getName() + "' has empty message");
        }
    }

    @Override
    public void visit(TransformStep step) {
        if (step.getName() == null || step.getName().isEmpty()) {
            errors.add("TransformStep has empty name");
        }
        if (step.getField() == null || step.getField().isEmpty()) {
            errors.add("TransformStep '" + step.getName() + "' missing field");
        }
        if (step.getOp() == null || step.getOp().isEmpty()) {
            errors.add("TransformStep '" + step.getName() + "' missing op");
        }
    }

    @Override
    public void visit(FilterStep step) {
        if (step.getName() == null || step.getName().isEmpty()) {
            errors.add("FilterStep has empty name");
        }
        if (step.getField() == null || step.getField().isEmpty()) {
            errors.add("FilterStep '" + step.getName() + "' missing field");
        }
        if (step.getContains() == null || step.getContains().isEmpty()) {
            errors.add("FilterStep '" + step.getName() + "' missing contains");
        }
    }

    @Override
    public void visit(CompositeStep step) {
        if (step.getName() == null || step.getName().isEmpty()) {
            errors.add("CompositeStep has empty name");
        }
        // children traversal already handled in CompositeStep.accept()
    }
}