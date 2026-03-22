import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import workflow.model.*;
import workflow.iterator.*;
import workflow.visitor.*;
import workflow.editor.*;

public class DepthFirstIteratorTest {

    @Test
    void dfsTraversalOrderIsCorrect() {
        Step a = new DelayStep("A", 100);
        Step c = new DelayStep("C", 100);
        Step d = new DelayStep("D", 100);
        CompositeStep b = new CompositeStep("B", List.of(c, d));
        Step e = new DelayStep("E", 100);

        CompositeStep root = new CompositeStep("Root", List.of(a, b, e));

        DepthFirstIterator it = new DepthFirstIterator(root);

        List<String> names = new ArrayList<>();
        while (it.hasNext()) {
            names.add(it.next().getName());
        }

        assertEquals(List.of("Root", "A", "B", "C", "D", "E"), names);
    }
}