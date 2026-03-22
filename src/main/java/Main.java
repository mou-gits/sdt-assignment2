import org.json.JSONArray;
import org.json.JSONObject;
import workflow.factory.StepFactory;
import workflow.iterator.DepthFirstIterator;
import workflow.iterator.LinearIterator;
import workflow.model.Step;
import workflow.visitor.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        List<String> workflowFiles = List.of(
                "src/main/resources/workflows/workflow1.json",
                "src/main/resources/workflows/workflow2.json",
                "src/main/resources/workflows/workflow3.json",
                "src/main/resources/workflows/workflow4.json"
        );

        for (String file : workflowFiles) {
            System.out.println("\n==============================");
            System.out.println("Loading workflow: " + file);
            System.out.println("==============================");

            Map<String, Object> config = loadJsonAsMap(file);

            StepFactory factory = new StepFactory();
            Step root = factory.create(config);

            runAllFeatures(root);
        }
    }

    private static void runAllFeatures(Step root) {

        System.out.println("\n--- PRETTY PRINT ---");
        PrettyPrintVisitor pp = new PrettyPrintVisitor();
        root.accept(pp);
        System.out.println(pp.getOutput());

        System.out.println("--- COST VISITOR ---");
        CostVisitor cost = new CostVisitor();
        root.accept(cost);
        System.out.println("Total cost: " + cost.getTotalCost());

        System.out.println("--- VALIDATION VISITOR ---");
        ValidationVisitor val = new ValidationVisitor();
        root.accept(val);
        System.out.println("Errors: " + val.getErrors());

        System.out.println("--- DFS ITERATOR ---");
        DepthFirstIterator dfs = new DepthFirstIterator(root);
        while (dfs.hasNext()) {
            System.out.println(dfs.next().getName());
        }

        System.out.println("--- LINEAR ITERATOR ---");
        LinearIterator lin = new LinearIterator(root);
        while (lin.hasNext()) {
            System.out.println(lin.next().getName());
        }
    }

    // ---------------------------
    // JSON → Map Conversion
    // ---------------------------

    private static Map<String, Object> loadJsonAsMap(String filename) throws IOException {
        String content = Files.readString(Paths.get(filename));
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
