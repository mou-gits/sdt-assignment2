package workflow.model;

import workflow.visitor.Visitor;

public class TransformStep implements Step {

    private String name;
    private String field;
    private String op;

    public TransformStep(String name, String field, String op) {
        this.name = name;
        this.field = field;
        this.op = op;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getField() {
        return field;
    }

    public String getOp() {
        return op;
    }

    @Override
    public void execute() {
        System.out.println("Transform " + field + " using " + op);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}