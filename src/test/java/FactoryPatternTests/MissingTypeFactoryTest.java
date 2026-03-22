package FactoryPatternTests;

import org.junit.jupiter.api.Test;
import workflow.factory.StepFactory;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MissingTypeFactoryTest {

    @Test
    void missingTypeThrows() {
        StepFactory factory = new StepFactory();

        Map<String, Object> config = Map.of(
                "name", "NoType"
        );

        assertThrows(IllegalArgumentException.class, () -> factory.create(config));
    }
}
