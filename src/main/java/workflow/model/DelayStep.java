package workflow.model;

import workflow.visitor.Visitor;

public class DelayStep implements Step {

    private String name;
    private int ms;

    public DelayStep(String name, int ms) {
        this.name = name;
        this.ms = ms;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getMs() {
        return ms;
    }

    @Override
    public void execute() {
        System.out.println("Delay for " + ms + " ms");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}