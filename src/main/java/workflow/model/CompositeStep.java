package workflow.model;

import workflow.visitor.Visitor;

import java.util.List;

public class CompositeStep implements Step {

    private String name;
    private List<Step> children;

    public CompositeStep(String name, List<Step> children) {
        this.name = name;
        this.children = children;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<Step> getChildren() {
        return children;
    }

    @Override
    public void execute() {
        for (Step step : children) {
            step.execute();
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        for (Step child : children) {
            child.accept(visitor);
        }
        visitor.leaveComposite(this);  // exit composite
    }
}