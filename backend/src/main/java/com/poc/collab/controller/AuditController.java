package com.poc.collab.controller;

import com.poc.collab.model.AuditLog;
import com.poc.collab.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequiredArgsConstructor
public class AuditController {
    private final DocumentService service;
    @GetMapping("/api/documents/{id}/audit") public List<AuditLog> audit(@PathVariable String id) { return service.audit(id); }
}
