package workflow;

import java.util.List;

public class WorkflowEditor {

    private List<Step> steps;

    private Stack<List<Step>> undoStack = new Stack<>();
    private Stack<List<Step>> redoStack = new Stack<>();

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

    // Shallow copy of workflow steps (enough if steps themselves aren’t mutated)
    private List<Step> copySteps(List<Step> original) {
        return new ArrayList<>(original);
    }
}