package com.poc.collab.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Document(indexName = "documents")
public class SearchDocument {
    @Id private String id;
    private String name;
    private String content;
    private String ownerId;
}
