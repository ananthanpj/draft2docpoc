package com.poc.collab.dto;

import lombok.Data;

@Data
public class EditRequest {
    private String userId;
    private String content;
}
