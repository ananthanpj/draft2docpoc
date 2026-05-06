package com.poc.collab.repository;

import com.poc.collab.model.SearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SearchDocumentRepository extends ElasticsearchRepository<SearchDocument, String> {
    List<SearchDocument> findByNameContainingOrContentContainingOrOwnerId(String name, String content, String ownerId);
}
