package com.poc.collab.repository;

import com.poc.collab.model.AuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuditLogRepository extends MongoRepository<AuditLog, String> {
    List<AuditLog> findByDocumentIdOrderByTimestampDesc(String documentId);
}
