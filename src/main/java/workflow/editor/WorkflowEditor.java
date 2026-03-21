package workflow.editor;

import workflow.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class WorkflowEditor {

    private List<Step> steps;

    private final Stack<List<Step>> undoStack = new Stack<>();
    private final Stack<List<Step>> redoStack = new Stack<>();

    public WorkflowEditor(List<Step> steps) {
        this.steps = new ArrayList<>(steps); // start with a copy
    }

    // Adds a new step and saves current state for undo
    public void addStep(Step step) {
        saveStateForUndo();
        steps.add(step);
        redoStack.clear(); // clear redo history after new change
    }

    // Removes a step by index and saves current state for undo
    public void removeStep(int index) {
        if (index >= 0 && index < steps.size()) {
            saveStateForUndo();
            steps.remove(index);
            redoStack.clear();
        }
    }

    // Returns current workflow steps
    public List<Step> getSteps() {
        return steps;
    }

    // Undo last change
    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(copySteps(steps));
            steps = undoStack.pop();
        }
    }

    // Redo last undone change
    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(copySteps(steps));
            steps = redoStack.pop();
        }
    }

    // Helper to save current state to undo stack
    private void saveStateForUndo() {
        undoStack.push(copySteps(steps));
    }

    // Normal  copy of workflow steps This is not what the assignment wants
    // private List<Step> copySteps(List<Step> original) {
    //    return new ArrayList<>(original);
    //}

    private List<Step> copySteps(List<Step> original) {
        List<Step> copy = new ArrayList<>();
        for (Step step : original) {
            copy.add(cloneStep(step));
        }
        return copy;
    }

    //Need to add a clone method
    private Step cloneStep(Step step) {

        if (step instanceof DelayStep) {
            DelayStep s = (DelayStep) step;
            return new DelayStep(s.getName(), s.getMs());
        }

        if (step instanceof NotifyStep) {
            NotifyStep s = (NotifyStep) step;
            return new NotifyStep(s.getName(), s.getMessage());
        }

        if (step instanceof TransformStep) {
            TransformStep s = (TransformStep) step;
            return new TransformStep(s.getName(), s.getField(), s.getOp());
        }

        if (step instanceof FilterStep) {
            FilterStep s = (FilterStep) step;
            return new FilterStep(s.getName(), s.getField(), s.getContains());
        }

        if (step instanceof CompositeStep) {
            CompositeStep s = (CompositeStep) step;

            List<Step> childCopies = new ArrayList<>();
            for (Step child : s.getChildren()) {
                childCopies.add(cloneStep(child));
            }

            return new CompositeStep(s.getName(), childCopies);
        }

        throw new IllegalArgumentException("Unknown step type");
    }

}