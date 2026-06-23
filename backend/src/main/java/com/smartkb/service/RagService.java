package com.smartkb.service;

import java.util.List;

public interface RagService {
    List<String> search(String queryText, int maxResults);
    boolean hasDocuments();
}