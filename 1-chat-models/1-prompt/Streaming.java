///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS dev.langchain4j:langchain4j-open-ai:0.34.0
//DEPS org.slf4j:slf4j-simple:1.7.32
//RUNTIME_OPTIONS -Dorg.slf4j.simpleLogger.defaultLogLevel=debug

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;

import java.time.Duration;

class Streaming {

    // --------------------------
    //  add streaming (to console)
    // --------------------------
    //      - create a StreamingChatLanguageModel of type OpenAi
    //      - write an answer to the console and observe how it is rendered token per token
    public static void main(String[] args) {
        StreamingChatLanguageModel model = OpenAiStreamingChatModel.withApiKey(System.getenv("OPENAI_API_KEY"));
        System.out.println("Answer: ");

        model.generate("Write a poem about unicorns and grizzly bears",
                new StreamingResponseHandler<AiMessage>() {
                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("Whoopsie!");
                    }
                });

    }

}