import workflow.model.*;
import workflow.visitor.CostVisitor;
import workflow.visitor.PrettyPrintVisitor;
import workflow.visitor.ValidationVisitor;

import java.util.*;

public class Main {
    static void main() {
        Step bad = new DelayStep("", -10);
        CompositeStep root = new CompositeStep("Root", List.of(bad));

        ValidationVisitor v = new ValidationVisitor();
        root.accept(v);

        System.out.println(v.getErrors());
    }
}
