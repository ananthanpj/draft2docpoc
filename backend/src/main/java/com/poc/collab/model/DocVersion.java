package com.poc.collab.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Document(collection = "versions")
public class DocVersion {
    @Id private String id;
    private String documentId;
    private String content;
    private String editedBy;
    private Instant timestamp;
}
