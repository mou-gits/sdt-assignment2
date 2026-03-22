import org.junit.jupiter.api.Test;
import workflow.editor.WorkflowEditor;
import workflow.model.DelayStep;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkflowEditorUndoRedoTest {

    @Test
    void undoRestoresPreviousState() {
        WorkflowEditor editor = new WorkflowEditor(new ArrayList<>());

        editor.addStep(new DelayStep("A", 100));
        editor.addStep(new DelayStep("B", 200));

        assertEquals(2, editor.getSteps().size());

        editor.undo();

        assertEquals(1, editor.getSteps().size());
        assertEquals("A", editor.getSteps().get(0).getName());
    }

    @Test
    void redoReappliesState() {
        WorkflowEditor editor = new WorkflowEditor(new ArrayList<>());

        editor.addStep(new DelayStep("A", 100));
        editor.addStep(new DelayStep("B", 200));

        editor.undo();
        editor.redo();

        assertEquals(2, editor.getSteps().size());
        assertEquals("B", editor.getSteps().get(1).getName());
    }
}