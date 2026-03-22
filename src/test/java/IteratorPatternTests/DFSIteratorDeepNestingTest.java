package IteratorPatternTests;
import org.junit.jupiter.api.Test;
import workflow.model.*;
        import workflow.iterator.DepthFirstIterator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DFSIteratorDeepNestingTest {

    @Test
    void dfsHandlesDeepNesting() {
        CompositeStep level3 = new CompositeStep("L3", List.of(new DelayStep("D3", 1)));
        CompositeStep level2 = new CompositeStep("L2", List.of(level3));
        CompositeStep level1 = new CompositeStep("L1", List.of(level2));

        DepthFirstIterator it = new DepthFirstIterator(level1);

        List<String> names = new ArrayList<>();
        while (it.hasNext()) names.add(it.next().getName());

        assertEquals(List.of("L1", "L2", "L3", "D3"), names);
    }
}
