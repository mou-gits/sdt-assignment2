import org.json.JSONArray;
import org.json.JSONObject;
import workflow.factory.StepFactory;
import workflow.iterator.DepthFirstIterator;
import workflow.iterator.LinearIterator;
import workflow.model.Step;
import workflow.visitor.CostVisitor;
import workflow.visitor.PrettyPrintVisitor;
import workflow.visitor.ValidationVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        String[] workflowFiles = {
                "src/main/resources/workflows/workflow1.json",
                "src/main/resources/workflows/workflow2.json",
                "src/main/resources/workflows/workflow3.json",
                "src/main/resources/workflows/workflow4.json"
        };

        for (String file : workflowFiles) {
            System.out.println("==============================");
            System.out.println("Loading workflow: " + file);
            System.out.println("==============================");

            Map<String, Object> config = loadJsonAsMap(file);
            Step root = new StepFactory().create(config);

            runPrettyPrint(root);
            runCostVisitor(root);
            runValidationVisitor(root);
            runDFS(root);
            runLinear(root);

            System.out.println();
        }
    }

    // ------------------------------------------------------------
    // Pretty Print
    // ------------------------------------------------------------
    private static void runPrettyPrint(Step root) {
        System.out.println("\n--- PRETTY PRINT ---");
        PrettyPrintVisitor pp = new PrettyPrintVisitor();
        root.accept(pp);
        System.out.print(pp.getOutput());
    }

    // ------------------------------------------------------------
    // Cost Visitor
    // ------------------------------------------------------------
    private static void runCostVisitor(Step root) {
        System.out.println("--- COST VISITOR ---");
        CostVisitor cv = new CostVisitor();
        root.accept(cv);
        System.out.println("Total cost: " + cv.getTotalCost());
    }

    // ------------------------------------------------------------
    // Validation Visitor
    // ------------------------------------------------------------
    private static void runValidationVisitor(Step root) {
        System.out.println("--- VALIDATION VISITOR ---");
        ValidationVisitor vv = new ValidationVisitor();
        root.accept(vv);
        System.out.println("Errors: " + vv.getErrors());
    }

    // ------------------------------------------------------------
    // DFS Iterator
    // ------------------------------------------------------------
    private static void runDFS(Step root) {
        System.out.println("--- DFS ITERATOR ---");
        DepthFirstIterator it = new DepthFirstIterator(root);
        while (it.hasNext()) {
            System.out.println(it.next().getName());
        }
    }

    // ------------------------------------------------------------
    // Linear Iterator
    // ------------------------------------------------------------
    private static void runLinear(Step root) {
        System.out.println("--- LINEAR ITERATOR ---");
        LinearIterator it = new LinearIterator(root);
        while (it.hasNext()) {
            System.out.println(it.next().getName());
        }
    }

    // ------------------------------------------------------------
    // JSON Loading Helpers
    // ------------------------------------------------------------
    private static Map<String, Object> loadJsonAsMap(String path) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(path)));
        JSONObject json = new JSONObject(content);
        return jsonToMap(json);
    }

    private static Map<String, Object> jsonToMap(JSONObject obj) {
        Map<String, Object> map = new HashMap<>();
        for (String key : obj.keySet()) {
            Object value = obj.get(key);
            if (value instanceof JSONObject) {
                map.put(key, jsonToMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                map.put(key, jsonToList((JSONArray) value));
            } else {
                map.put(key, value);
            }
        }
        return map;
    }

    private static List<Object> jsonToList(JSONArray arr) {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Object value = arr.get(i);
            if (value instanceof JSONObject) {
                list.add(jsonToMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                list.add(jsonToList((JSONArray) value));
            } else {
                list.add(value);
            }
        }
        return list;
    }
}
