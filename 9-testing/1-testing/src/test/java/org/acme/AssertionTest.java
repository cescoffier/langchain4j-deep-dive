package org.acme;

import dev.langchain4j.model.chat.ChatLanguageModel;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import org.acme.utils.JudgeModelAssertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@QuarkusTest
@Disabled
public class AssertionTest {

    @Inject
    ChatLanguageModel judge;

    @Test
    void test() {
        String response = RestAssured.given().body("""
                The Vegas algorithm is a Monte Carlo integration technique that improves upon simple random sampling by adapting the sampling distribution based on the behavior of the integrand. It is primarily used for numerical integration in high-dimensional spaces, particularly in physics and complex simulations.
                
                The core idea behind Vegas is to reduce the variance of the estimate by sampling more frequently in regions where the integrand contributes more to the integral. It achieves this by using importance sampling and adapting the sampling distribution over several iterations.
                
                How it Works:
                	1.	Start with uniform random sampling in all dimensions.
                	2.	Divide the integration domain into bins (or cells) along each axis.
                	3.	Estimate the function’s behavior and assign weights to bins where the function is larger.
                	4.	In subsequent iterations, adjust the sampling distribution to focus on the more “important” regions.
                	5.	Repeat the process to refine the sampling grid and improve accuracy.
                
                Vegas does this adaptively, learning the best sampling distribution as it goes.
                """)
                .that().post("/summary").asPrettyString();

        JudgeModelAssertions.with(judge).assertThat(response)
                .satisfies("The response should be a summary of the input text, highlighting the key points and using bullet points.")
                .satisfies("The summary should not include more than 5 bullet points.")
                .satisfies("the summary should be about the Vegas algorithm");
    }

}
