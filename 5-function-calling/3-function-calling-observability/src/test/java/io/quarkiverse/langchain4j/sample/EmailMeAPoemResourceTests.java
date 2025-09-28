package io.quarkiverse.langchain4j.sample;

import io.quarkiverse.mailpit.test.InjectMailbox;
import io.quarkiverse.mailpit.test.Mailbox;
import io.quarkiverse.mailpit.test.WithMailbox;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junitpioneer.jupiter.RetryingTest;

import static io.restassured.RestAssured.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;

@QuarkusTest
@WithMailbox
@Disabled("The model used during the test does not call the function")
class EmailMeAPoemResourceTests {
    @InjectSpy
    EmailService emailService;

    @InjectMailbox
    Mailbox mailbox;

    @AfterEach
    void afterEach() {
        this.mailbox.clear();
    }

    @RetryingTest(6)
    void itWorks() {
        get("/email-me-a-poem").then()
                .statusCode(200)
                .body(not(blankOrNullString()));

        verify(this.emailService).sendAnEmail(anyString());

        assertThat(this.mailbox.findFirst("demoer@langchain4j.ai"))
                .isNotNull()
                .satisfies(m -> {
                    assertThat(m.getTo().getFirst().getAddress()).isEqualTo("sendMeALetter@quarkus.io");
                    assertThat(m.getSubject()).isEqualTo("A poem for you");
                    assertThat(m.getText().strip()).isNotBlank();
                });
    }
}