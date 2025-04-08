package org.acme;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class MockTest {

    @InjectMock
    SummarizationService ai;

    private static final String LOREM = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
            Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
            Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. 
            Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
            """;

    @BeforeEach
    public void setup() {
        Mockito.when(ai.summarize(LOREM)).thenReturn("* Lorem\n* ipsum\n* dolor\n* sit\n* amet\n* consectetur");
    }


    @Test
    void testUsingEndpoint() {
        String result = RestAssured.given().body(LOREM)
                .that().post("/summary").asPrettyString();

        assertThat(result).isEqualTo("* Lorem\n* ipsum\n* dolor\n* sit\n* amet\n* consectetur");
    }


}
