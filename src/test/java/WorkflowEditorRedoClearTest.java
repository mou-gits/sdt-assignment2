import org.junit.jupiter.api.Test;
import workflow.editor.WorkflowEditor;
import workflow.model.DelayStep;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkflowEditorRedoClearTest {

    @Test
    void redoClearsAfterNewEdit() {
        WorkflowEditor editor = new WorkflowEditor(new ArrayList<>());

        editor.addStep(new DelayStep("A", 100));
        editor.addStep(new DelayStep("B", 200));

        editor.undo();
        assertEquals(1, editor.getSteps().size());

        editor.addStep(new DelayStep("C", 300));

        editor.redo();

        assertEquals(2, editor.getSteps().size());
        assertEquals("C", editor.getSteps().get(1).getName());
    }
}