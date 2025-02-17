///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS dev.langchain4j:langchain4j-open-ai:1.0.0-beta1
//DEPS org.slf4j:slf4j-simple:1.7.32
//RUNTIME_OPTIONS -Dorg.slf4j.simpleLogger.defaultLogLevel=debug

import java.util.concurrent.CountDownLatch;

import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;

class Streaming {

    // --------------------------
    //  add streaming (to console)
    // --------------------------
    //      - create a StreamingChatLanguageModel of type OpenAi
    //      - write an answer to the console and observe how it is rendered token per token
    public static void main(String[] args) throws InterruptedException {
        StreamingChatLanguageModel model = OpenAiStreamingChatModel.builder()
          .apiKey(System.getenv("OPENAI_API_KEY"))
          .modelName("gpt-4o")
          .build();

        final CountDownLatch latch = new CountDownLatch(1);
        System.out.println("Answer: ");

        model.chat("Write a poem about unicorns and grizzly bears",
                new StreamingChatResponseHandler() {
                    @Override
                    public void onPartialResponse(String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onCompleteResponse(ChatResponse response) {
                      System.out.println("Complete response:\n" + response.aiMessage().text());
                      latch.countDown();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("Whoopsie!");
                        throwable.printStackTrace(System.err);
                        latch.countDown();
                    }
                });

        latch.await();
        System.exit(0);
    }

}