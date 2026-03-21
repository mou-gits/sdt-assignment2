
package workflow;

import java.util.Iterator;
import java.util.*;

public class DepthFirstIterator implements Iterator<Step> {

    public stack<step> stack = new stack<>();

    @Override
    public boolean hasNext(){
        return !stack.isEmpty();
    }

    @Override
    public Step next(){
        if (!hasNext()){
            throw new NoSuchElementException();
        }

        Step current = stack.pop();

        if (current instanceof CompositeStecp){
            list<step> children = ((CompositeStep) current).getChildren;

            for (int i = children.size() - 1; i >= 0; i--){
                stack.push(children.get(i));
            }

        }
        return current;
    }

}


public class LinearItterator implements Iterator<step>{
    private list<Step> steps;
    private int index = 0;

    public LinearIterator(List<step> steps){
        this.steps = steps;
    }

    public boolean hasNext(){
        return index < steps.size();
    }

    public Step next(){
        if(!hasNext()){
            Throw new NoSuchElementException();
        }

        return steps.get(index++);
    }
}