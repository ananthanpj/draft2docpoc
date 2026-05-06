package com.poc.collab.repository;

import com.poc.collab.model.DocVersion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VersionRepository extends MongoRepository<DocVersion, String> {
    List<DocVersion> findByDocumentIdOrderByTimestampDesc(String documentId);
}
