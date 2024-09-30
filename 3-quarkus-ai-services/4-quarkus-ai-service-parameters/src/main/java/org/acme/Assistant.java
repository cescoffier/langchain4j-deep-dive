package org.acme;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

import java.util.List;

@RegisterAiService
@SystemMessage("You are a useful AI assistant expert in NBA.")
interface Assistant {

    @UserMessage("""
        What are the  {number}th last team in which {player} played?
        Only return the team names.
    """)
    List<String> ask(int number, String player);

    @UserMessage("""
        What are the last team in which {question.player} played?
        Return the team and the last season.
    """)
    Entry ask(MyHttpEndpoint.Question question);


    record Entry(String team, String years) {}

}
