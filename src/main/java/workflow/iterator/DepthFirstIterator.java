package workflow.iterator;

import workflow.model.Step;
import workflow.model.CompositeStep;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

public class DepthFirstIterator implements Iterator<Step> {

    private Stack<Step> stack = new Stack<>();

    // Constructor REQUIRED by test
    public DepthFirstIterator(Step root) {
        if (root != null) {
            stack.push(root);
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public Step next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        Step current = stack.pop();

        // If composite, push children in reverse order
        if (current instanceof CompositeStep) {
            List<Step> children = ((CompositeStep) current).getChildren();

            for (int i = children.size() - 1; i >= 0; i--) {
                stack.push(children.get(i));
            }
        }

        return current;
    }
}