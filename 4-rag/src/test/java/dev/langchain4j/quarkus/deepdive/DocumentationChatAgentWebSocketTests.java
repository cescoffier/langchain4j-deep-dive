package dev.langchain4j.quarkus.deepdive;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import jakarta.inject.Inject;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.websockets.next.CloseReason;
import io.quarkus.websockets.next.OnClose;
import io.quarkus.websockets.next.OnError;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocketClient;
import io.quarkus.websockets.next.WebSocketClientConnection;
import io.quarkus.websockets.next.WebSocketConnector;

@QuarkusTest
class DocumentationChatAgentWebSocketTests {
	@TestHTTPResource("/")
	URI appRootUrl;

	@Inject
	WebSocketConnector<WsTestClient> connector;

	@BeforeEach
	void beforeEach() {
		WsTestClient.MESSAGES.clear();
	}

	@Test
	void itWorks() throws InterruptedException {
		var connection = connectClient();

		await()
			.atMost(Duration.ofMinutes(5))
			.until(() -> "Welcome to Quarkus Help! I'm Quarky. How can I make your day more Quarkus-y?".equals(WsTestClient.MESSAGES.poll()));

		connection.sendTextAndAwait("What is Quarkus?");

		await()
			.atMost(Duration.ofMinutes(5))
			.until(() -> WsTestClient.MESSAGES.size() >= 1);

		assertThat(WsTestClient.MESSAGES)
			.isNotNull()
			.hasSizeGreaterThanOrEqualTo(1);

		assertThat(WsTestClient.MESSAGES.take())
			.isNotBlank();

		connection.closeAndAwait();
	}

	private WebSocketClientConnection connectClient() {
		var connection = this.connector
			.baseUri(this.appRootUrl)
			.connectAndAwait();

		waitForClientToStart();

		return connection;
	}

	private static void waitForClientToStart() {
		await()
			.atMost(Duration.ofMinutes(5))
			.until(() -> "CONNECT".equals(WsTestClient.MESSAGES.poll()));
	}

	@WebSocketClient(path = "/doc-support", clientId = "c1")
	static class WsTestClient {
		private final Logger logger = Logger.getLogger(WsTestClient.class);
		static final BlockingQueue<String> MESSAGES = new LinkedBlockingDeque<>();

		@OnOpen
		void open(WebSocketClientConnection connection) {
			this.logger.infof("[CLIENT] Opening endpoint %s", connection.id());
			MESSAGES.offer("CONNECT");
		}

		@OnTextMessage
		void textMessage(String message) {
			this.logger.infof("[CLIENT] Got message: %s", message);
			MESSAGES.offer(message);
		}

		@OnError
		void error(Throwable error) {
			this.logger.errorf(error, "[CLIENT] Got error: %s", error.getMessage());
		}

		@OnClose
		void close(CloseReason closeReason, WebSocketClientConnection connection) {
			this.logger.infof("[CLIENT] Closing endpoint %s: %s: %s", connection.id(), closeReason.getCode(), closeReason.getMessage());
		}
	}
}