package com.poc.collab.controller;

import com.poc.collab.dto.DocumentRequest;
import com.poc.collab.dto.EditRequest;
import com.poc.collab.dto.LockRequest;
import com.poc.collab.model.Document;
import com.poc.collab.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequiredArgsConstructor @RequestMapping("/api/documents")
public class DocumentController {
    private final DocumentService service;
    @PostMapping public Document create(@RequestBody DocumentRequest request) { return service.create(request); }
    @GetMapping("/{id}") public Document get(@PathVariable String id) { return service.getById(id); }
    @PutMapping("/{id}") public Document update(@PathVariable String id, @RequestBody EditRequest request) { return service.update(id, request); }
    @PostMapping("/{id}/lock") public Document lock(@PathVariable String id, @RequestBody LockRequest request) { return service.lock(id, request.getUserId()); }
    @PostMapping("/{id}/unlock") public Document unlock(@PathVariable String id, @RequestBody LockRequest request) { return service.unlock(id, request.getUserId()); }
}
