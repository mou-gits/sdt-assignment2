package MementoPatternTests;
import org.junit.jupiter.api.Test;
import workflow.editor.WorkflowEditor;
import workflow.model.*;

        import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WorkflowEditorUndoRedoWithCompositeTest {

    @Test
    void undoRedoWorksWithComposite() {
        WorkflowEditor editor = new WorkflowEditor(new ArrayList<>());

        CompositeStep comp = new CompositeStep("C", List.of(
                new DelayStep("D1", 100)
        ));

        editor.addStep(comp);
        assertEquals(1, editor.getSteps().size());

        editor.undo();
        assertEquals(0, editor.getSteps().size());

        editor.redo();
        assertEquals(1, editor.getSteps().size());
    }
}
