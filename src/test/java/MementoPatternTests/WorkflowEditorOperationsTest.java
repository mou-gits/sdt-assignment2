package MementoPatternTests;

import org.junit.jupiter.api.Test;
import workflow.editor.WorkflowEditor;
import workflow.model.DelayStep;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkflowEditorOperationsTest {

    @Test
    void addRemoveEditOperationsBehaveCorrectly() {
        WorkflowEditor editor = new WorkflowEditor(new ArrayList<>());

        editor.addStep(new DelayStep("A", 100));
        editor.addStep(new DelayStep("B", 200));
        assertEquals(2, editor.getSteps().size());

        editor.removeStep(0);
        assertEquals(1, editor.getSteps().size());
        assertEquals("B", editor.getSteps().get(0).getName());

        editor.editStepName(0, "B-Edited");
        assertEquals("B-Edited", editor.getSteps().get(0).getName());
    }
}