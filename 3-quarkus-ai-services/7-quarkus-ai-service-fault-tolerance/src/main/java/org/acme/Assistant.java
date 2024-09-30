package org.acme;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import java.time.temporal.ChronoUnit;
import java.util.List;

@RegisterAiService
@SystemMessage("You are a useful AI assistant expert in NBA.")
interface Assistant {

    @UserMessage("""
        What are the last team in which {question.player} played?
        Return the team and the last season.
    """)
    @Retry(maxRetries = 2)
    @Timeout(value = 1, unit = ChronoUnit.MINUTES)
    @Fallback(fallbackMethod = "fallback")
    Entry ask(MyHttpEndpoint.Question question);


    record Entry(String team, String years) {}

    default Entry fallback(MyHttpEndpoint.Question question) {
        return new Entry("Unknown", "Unknown");
    }

}
