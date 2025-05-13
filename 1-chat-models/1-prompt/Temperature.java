///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS dev.langchain4j:langchain4j-open-ai:1.0.0-rc1
//DEPS org.slf4j:slf4j-simple:1.7.32
//RUNTIME_OPTIONS -Dorg.slf4j.simpleLogger.defaultLogLevel=debug

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import java.time.Duration;

class Temperature {

    // --------------------------
    // control more parameters
    // --------------------------
    //      - create another ChatModel of type OpenAi
    //      - set your key, some model parameters and set logging on for requests and responses
    //      - generate an answer based on your input and print to console
    //
    // Parameter settings depend on the model, and can usually be found on the model provider's website
    // Eg. for OpenAI's models: https://platform.openai.com/docs/api-reference/chat/create
    // The cost per token can be found in the provider's terms of use (not applicable for locally running models)
    public static void main(String[] args) {
        ChatModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .modelName("gpt-4o")
                .timeout(Duration.ofSeconds(30))
                .temperature(0.5)
                .logRequests(true)
                .logResponses(true)
                .build();
        String answer = model.chat("Name five words that developers hate to hear most");
        System.out.println("Answer:\n" + answer);
    }

}