package workflow.model;

import workflow.visitor.Visitor;

public class NotifyStep implements Step {

    private String name;
    private String message;

    public NotifyStep(String name, String message) {
        this.name = name;
        this.message = message;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void execute() {
        System.out.println("Notify: " + message);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}