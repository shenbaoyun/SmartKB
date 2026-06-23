package com.smartkb.service.impl;

import com.smartkb.service.RagService;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RagServiceImpl implements RagService {
    private static final Logger log = LoggerFactory.getLogger(RagServiceImpl.class);
    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore embeddingStore;

    public RagServiceImpl(EmbeddingModel embeddingModel, EmbeddingStore embeddingStore) {
        this.embeddingModel = embeddingModel;
        this.embeddingStore = embeddingStore;
    }

    @Override
    public List<String> search(String queryText, int maxResults) {
        if (!hasDocuments()) return Collections.emptyList();
        Embedding queryEmbedding = embeddingModel.embed(queryText).content();
        EmbeddingSearchRequest searchReq = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding).maxResults(maxResults).minScore(0.5).build();
        List<EmbeddingMatch<TextSegment>> matches = embeddingStore.search(searchReq).matches();
        return matches.stream().map(m -> m.embedded().text()).collect(Collectors.toList());
    }

    @Override
        public boolean hasDocuments() {
        return false; // Disabled until valid API key is configured
    }
    private boolean _hasDocuments() {
        try {
            Embedding dummy = embeddingModel.embed("test").content();
            EmbeddingSearchRequest req = EmbeddingSearchRequest.builder()
                    .queryEmbedding(dummy).maxResults(1).build();
            return !embeddingStore.search(req).matches().isEmpty();
        } catch (Exception e) { return false; }
    }
}