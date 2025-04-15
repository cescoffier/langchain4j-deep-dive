///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS dev.langchain4j:langchain4j-open-ai:1.0.0-beta3

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

class Prompt {

    // -------------------
    // the shortest way
    // -------------------
    //      - create a ChatLanguageModel of type OpenAi
    //      - set your key
    //      - generate an answer based on your input and print to console
    public static void main(String[] args) {
        ChatLanguageModel model = OpenAiChatModel.builder()
          .apiKey(System.getenv("OPENAI_API_KEY"))
          .modelName("gpt-4o")
          .build();
        String answer = model.chat("Say Hello World");
        System.out.println("Answer: " + answer);
    }

}