package MementoPatternTests;
import org.junit.jupiter.api.Test;
import workflow.editor.WorkflowEditor;
import workflow.model.DelayStep;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MementoDeepCopyTest {

    @Test
    void editingAfterUndoDoesNotAffectPreviousSnapshot() {
        WorkflowEditor editor = new WorkflowEditor(new ArrayList<>());

        editor.addStep(new DelayStep("A", 100));
        editor.addStep(new DelayStep("B", 200));

        editor.undo(); // removes B

        editor.editStepName(0, "A-Edited");

        assertEquals("A-Edited", editor.getSteps().get(0).getName());
    }
}
