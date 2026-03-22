package MementoPatternTests;
import org.junit.jupiter.api.Test;
import workflow.editor.WorkflowEditor;
import workflow.model.*;

        import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MementoDeepCopyCompositeElementTest {

    @Test
    void compositeIsDeepCopied() {
        CompositeStep comp = new CompositeStep("C", List.of(
                new DelayStep("D1", 100)
        ));

        WorkflowEditor editor = new WorkflowEditor(new ArrayList<>());
        editor.addStep(comp);

        editor.undo(); // snapshot contains composite

        editor.addStep(new DelayStep("X", 1)); // new edit

        assertEquals(1, editor.getSteps().size());
    }
}
