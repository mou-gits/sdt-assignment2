package workflow.iterator;

import workflow.model.CompositeStep;
import workflow.model.Step;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class LinearIterator implements Iterator<Step> {

    private List<Step> steps;
    private int index = 0;

    public LinearIterator(Step root) {
        if (root instanceof CompositeStep) {
            this.steps = ((CompositeStep) root).getChildren();
        } else {
            this.steps = List.of(root);
        }
    }

    @Override
    public boolean hasNext() {
        return index < steps.size();
    }

    @Override
    public Step next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return steps.get(index++);
    }
}