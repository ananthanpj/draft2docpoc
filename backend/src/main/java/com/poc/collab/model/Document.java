package com.poc.collab.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@org.springframework.data.mongodb.core.mapping.Document(collection = "documents")
public class Document {
    @Id private String id;
    private String name;
    private String content;
    private String ownerId;
    @Builder.Default private boolean isPublic = true;
    @Builder.Default private PermissionType permissionType = PermissionType.PUBLIC;
    @Builder.Default private List<String> permittedUsers = new ArrayList<>();
    private String lockedBy;
    private Instant lockedAt;
    private Instant createdAt;
    private Instant updatedAt;
}
