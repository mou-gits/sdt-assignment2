package workflow.factory;

import workflow.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StepFactory {

    public Step create(Map<String, Object> config) {

        String type = (String) config.get("type");
        String name = (String) config.get("name");

        if (type == null) {
            throw new IllegalArgumentException("Missing step type");
        }

        switch (type.toLowerCase()) {

            case "delay":
                int ms = (int) config.get("ms");
                return new DelayStep(name, ms);

            case "notify":
                String message = (String) config.get("message");
                return new NotifyStep(name, message);

            case "transform":
                String field = (String) config.get("field");
                String op = (String) config.get("op");
                return new TransformStep(name, field, op);

            case "filter":
                String contains = (String) config.get("contains");
                String filterField = (String) config.get("field");
                return new FilterStep(name, filterField, contains);

            case "composite":
                List<Map<String, Object>> childConfigs =
                        (List<Map<String, Object>>) config.get("steps");

                List<Step> children = new ArrayList<>();

                if (childConfigs != null) {
                    for (Map<String, Object> child : childConfigs) {
                        children.add(create(child)); // 🔥 RECURSION
                    }
                }

                return new CompositeStep(name, children);

            default:
                throw new IllegalArgumentException("Unknown step type: " + type);
        }
    }
}