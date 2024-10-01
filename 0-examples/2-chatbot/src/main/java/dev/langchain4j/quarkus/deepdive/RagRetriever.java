package dev.langchain4j.quarkus.deepdive;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.embedding.onnx.bgesmallenq.BgeSmallEnQuantizedEmbeddingModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.injector.ContentInjector;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import io.quarkiverse.langchain4j.pgvector.PgVectorEmbeddingStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.util.List;

public class RagRetriever {

    @Produces
    @ApplicationScoped
    public RetrievalAugmentor create(PgVectorEmbeddingStore store, BgeSmallEnQuantizedEmbeddingModel model) {
        EmbeddingStoreContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingModel(model)
                .embeddingStore(store)
                .maxResults(3)
                .build();

        return DefaultRetrievalAugmentor.builder()
                .contentRetriever(contentRetriever)
                .contentInjector(new ContentInjector() {
                    @Override
                    public UserMessage inject(List<Content> list, UserMessage userMessage) {
                        StringBuffer prompt = new StringBuffer(userMessage.singleText());
                        prompt.append("\nPlease, only use the following information:\n");
                        list.forEach(content -> prompt.append("- ").append(content.textSegment().text()).append("\n"));
                        return new UserMessage(prompt.toString());
                    }
                })
                .build();
    }
}
