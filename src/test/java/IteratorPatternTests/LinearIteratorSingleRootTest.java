package IteratorPatternTests;
import org.junit.jupiter.api.Test;
import workflow.model.DelayStep;
import workflow.iterator.LinearIterator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LinearIteratorSingleRootTest {

    @Test
    void returnsOnlyRootWhenNotComposite() {
        DelayStep root = new DelayStep("A", 1);

        LinearIterator it = new LinearIterator(root);

        List<String> names = new ArrayList<>();
        while (it.hasNext()) names.add(it.next().getName());

        assertEquals(List.of("A"), names);
    }
}
