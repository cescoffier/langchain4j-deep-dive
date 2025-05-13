package dev.langchain4j.quarkus.deepdive;

import java.nio.file.Path;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;

import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;

@ApplicationScoped
public class RagIngestion {

    /**
     * Ingests the documents from the given location into the embedding store.
     *
     * @param ev             the startup event to trigger the ingestion when the application starts
     * @param embeddingStore the embedding store the embedding store (PostGreSQL in our case)
     * @param embeddingModel the embedding model to use for the embedding (BGE-Small-EN-Quantized in our case)
     * @param documentsPath  the location of the documents to ingest
     */
    public void ingest(@Observes StartupEvent ev,
                       EmbeddingStore embeddingStore,
                       EmbeddingModel embeddingModel,
                       @ConfigProperty(name = "rag.location") Path documentsPath) {

        // cleanup the store to start fresh (just for demo purposes)
        embeddingStore.removeAll();

        var documentSplitter = DocumentSplitters.recursive(
            500,
            50,
            new OpenAiTokenizer(OpenAiChatModelName.GPT_4_O)
        );

        // Ingest the documents
        EmbeddingStoreIngestor.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .documentSplitter(documentSplitter)
                .build()
                .ingest(FileSystemDocumentLoader.loadDocumentsRecursively(documentsPath));

        Log.infof("Documents ingested successfully from %s", documentsPath);
    }
}
