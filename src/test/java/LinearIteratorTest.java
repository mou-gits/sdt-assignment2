import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import workflow.model.*;
import workflow.iterator.*;
import workflow.visitor.*;
import workflow.editor.*;


public class LinearIteratorTest {

    @Test
    void linearIteratorOrderIsCorrect() {
        Step a = new DelayStep("A", 100);
        CompositeStep b = new CompositeStep("B", List.of(
                new DelayStep("C", 100),
                new DelayStep("D", 100)
        ));
        Step e = new DelayStep("E", 100);

        CompositeStep root = new CompositeStep("Root", List.of(a, b, e));

        LinearIterator it = new LinearIterator(root);

        List<String> names = new ArrayList<>();
        while (it.hasNext()) {
            names.add(it.next().getName());
        }

        assertEquals(List.of("A", "B", "E"), names);
    }
}