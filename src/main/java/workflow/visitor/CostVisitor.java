package workflow.visitor;

import workflow.model.*;

public class CostVisitor implements Visitor {

    private int totalCost = 0;

    public int getTotalCost() {
        return totalCost;
    }

    @Override
    public void visit(DelayStep step) {
        int cost = (int) Math.ceil(step.getMs() / 100.0);
        totalCost += cost;
    }

    @Override
    public void visit(NotifyStep step) {
        totalCost += 2;
    }

    @Override
    public void visit(TransformStep step) {
        totalCost += 1;
    }

    @Override
    public void visit(FilterStep step) {
        totalCost += 1;
    }

    @Override
    public void visit(CompositeStep step) {
        // No cost for composite itself (optional)
    }
}