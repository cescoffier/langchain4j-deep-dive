package me.escoffier;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import org.jsoup.Jsoup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Callable;

@Command(name = "summarize", mixinStandardHelpOptions = true)
public class SummarizingCommand implements Callable<Integer> {

    @Parameters(paramLabel = "<url>",
        description = "The URL of the article to summarize")
    String url;

    @Inject SummarizerService service;

    @Override
    @ActivateRequestContext
    public Integer call() throws Exception {

        Log.infof("Summarizing article %s", url);
        Log.infof("Extracting content...");
        var content = extract(url);

        Log.infof("Summarizing content...");
        var summary = service.summarize(content);

        Log.infof("Summary: %s", summary);

        return 0;
    }

    private String extract(String url) throws IOException, URISyntaxException {
        return Jsoup.parse(new URI(url).toURL(), 10000).text();
    }

}
