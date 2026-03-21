package workflow.model;

import workflow.visitor.Visitor;

public class FilterStep implements Step {

    private String name;
    private String field;
    private String contains;

    public FilterStep(String name, String field, String contains) {
        this.name = name;
        this.field = field;
        this.contains = contains;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getField() {
        return field;
    }

    public String getContains() {
        return contains;
    }

    @Override
    public void execute() {
        System.out.println("Filter " + field + " contains " + contains);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}