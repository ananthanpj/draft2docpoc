package com.poc.collab.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Document(collection = "audit_logs")
public class AuditLog {
    @Id private String id;
    private String documentId;
    private String userId;
    private AuditAction action;
    private Instant timestamp;
}
