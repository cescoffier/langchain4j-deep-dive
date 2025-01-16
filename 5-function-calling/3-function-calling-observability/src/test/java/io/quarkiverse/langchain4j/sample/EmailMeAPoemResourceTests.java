package io.quarkiverse.langchain4j.sample;

import static io.restassured.RestAssured.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;

import io.quarkiverse.mailpit.test.InjectMailbox;
import io.quarkiverse.mailpit.test.Mailbox;
import io.quarkiverse.mailpit.test.WithMailbox;

@QuarkusTest
@WithMailbox
class EmailMeAPoemResourceTests {
  @InjectSpy
  EmailService emailService;

  @InjectMailbox
  Mailbox mailbox;

  @AfterEach
  void afterEach() {
    this.mailbox.clear();
  }

  @Test
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