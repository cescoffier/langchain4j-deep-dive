A RAG example that creates a complex `RetrievalAugmentor`, combining data from an embedding store as well as a web search of the quarkus.io site.

See [`application.properties`](src/main/resources/application.properties) and [`RagRetriever`](src/main/java/dev/langchain4j/quarkus/deepdive/RagRetriever.java).

There are some things commented out in `RagRetriever` that can be uncommented to change how things work:

- `.queryTransformer(new CompressingQueryTransformer(chatLanguageModel))`
- In the `getQueryRouter` method you can switch the `QueryRouter` to use a `LanguageModelQueryRouter`
- In the `getContentAggregator` method you can switch between a few options for defining a `ContentAggregator`

You will need the following environment variables to run, depending on which features you are using:
- `OPENAI_API_KEY` to make calls to OpenAI
- `TAVILY_API_KEY` to call the `WebSearchEngine`
- `COHERE_API_KEY` (optional) if you want to do re-ranking

To run the app simply run `./mvnw clean quarkus:dev`, then hit the `w` key in the Quarkus console to launch the application.

## Running with local Ollama instead
If you'd like to run with a local Ollama instance instead, first you need to ensure a local Ollama instance is running on port `11434` and the `llama3.2` model is pulled.

Then you can replace step 1 above with `./mvnw clean quarkus:dev -Dquarkus.profile=ollama,dev`