package com.poc.collab.dto;

import com.poc.collab.model.PermissionType;
import lombok.Data;

import java.util.List;

@Data
public class DocumentRequest {
    private String name;
    private String content;
    private String ownerId;
    private Boolean isPublic;
    private PermissionType permissionType;
    private List<String> permittedUsers;
}
