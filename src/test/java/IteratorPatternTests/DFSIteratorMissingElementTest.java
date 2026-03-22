package IteratorPatternTests;

import org.junit.jupiter.api.Test;
import workflow.model.DelayStep;
import workflow.iterator.DepthFirstIterator;

import static org.junit.jupiter.api.Assertions.*;

public class DFSIteratorMissingElementTest {

    @Test
    void nextThrowsWhenEmpty() {
        DepthFirstIterator it = new DepthFirstIterator(new DelayStep("A", 1));
        it.next(); // consume
        assertThrows(Exception.class, it::next);
    }
}
