package com.poc.collab.controller;

import com.poc.collab.model.DocVersion;
import com.poc.collab.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequiredArgsConstructor
public class VersionController {
    private final DocumentService service;
    @GetMapping("/api/documents/{id}/versions") public List<DocVersion> versions(@PathVariable String id) { return service.versions(id); }
    @GetMapping("/api/versions/{versionId}") public DocVersion version(@PathVariable String versionId) { return service.version(versionId); }
}
