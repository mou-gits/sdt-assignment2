package workflow.model;
import workflow.visitor.Visitor;

public interface Step {
    String getName();
    void execute();
    void accept(Visitor visitor);
}