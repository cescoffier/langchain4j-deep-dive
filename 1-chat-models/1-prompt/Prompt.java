///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS dev.langchain4j:langchain4j-open-ai:1.0.0-alpha1

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;

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
          .build();
        String answer = model.generate("Say Hello World");
        System.out.println("Answer: " + answer);
    }

}