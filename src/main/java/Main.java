import workflow.factory.StepFactory;
import workflow.iterator.DepthFirstIterator;
import workflow.iterator.LinearIterator;
import workflow.model.*;
import workflow.visitor.*;
import workflow.editor.WorkflowEditor;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("===== FACTORY DEMO =====");

        StepFactory factory = new StepFactory();

        // Build config (simulating JSON)
        Map<String, Object> step1 = Map.of(
                "type", "transform",
                "name", "TrimName",
                "field", "name",
                "op", "trim"
        );

        Map<String, Object> step2 = Map.of(
                "type", "filter",
                "name", "OnlyGmail",
                "field", "email",
                "contains", "@gmail.com"
        );

        Map<String, Object> step3 = Map.of(
                "type", "delay",
                "name", "Delay500",
                "ms", 500
        );

        Map<String, Object> nestedComposite = Map.of(
                "type", "composite",
                "name", "NestedGroup",
                "steps", List.of(step3)
        );

        Map<String, Object> rootComposite = Map.of(
                "type", "composite",
                "name", "RootWorkflow",
                "steps", List.of(step1, step2, nestedComposite)
        );

        Step root = factory.create(rootComposite);

        System.out.println("\n===== PRETTY PRINT (WITH SHRINKING CASCADE) =====");
        PrettyPrintVisitor printVisitor = new PrettyPrintVisitor();
        root.accept(printVisitor);

        System.out.println("\n===== COST VISITOR =====");
        CostVisitor costVisitor = new CostVisitor();
        root.accept(costVisitor);
        System.out.println("Total cost: " + costVisitor.getTotalCost());

        System.out.println("\n===== VALIDATION VISITOR =====");
        ValidationVisitor validationVisitor = new ValidationVisitor();
        root.accept(validationVisitor);
        System.out.println("Errors: " + validationVisitor.getErrors());

        System.out.println("\n===== DFS ITERATOR =====");
        DepthFirstIterator dfs = new DepthFirstIterator(root);
        while (dfs.hasNext()) {
            System.out.println(dfs.next().getName());
        }

        System.out.println("\n===== LINEAR ITERATOR =====");
        LinearIterator linear = new LinearIterator(root);
        while (linear.hasNext()) {
            System.out.println(linear.next().getName());
        }

        System.out.println("\n===== MEMENTO (UNDO/REDO) =====");

        List<Step> steps = new ArrayList<>();
        WorkflowEditor editor = new WorkflowEditor(steps);

        editor.addStep(new DelayStep("A", 100));
        editor.addStep(new DelayStep("B", 200));

        System.out.println("Steps after adding A, B: " + editor.getSteps().size());

        editor.undo();
        System.out.println("After undo: " + editor.getSteps().size());

        editor.redo();
        System.out.println("After redo: " + editor.getSteps().size());

        editor.undo();
        editor.addStep(new DelayStep("C", 300)); // clears redo

        System.out.println("After adding C: " + editor.getSteps().size());

        editor.redo(); // should do nothing
        System.out.println("After redo attempt: " + editor.getSteps().size());

        System.out.println("\n===== DONE =====");
    }
}
