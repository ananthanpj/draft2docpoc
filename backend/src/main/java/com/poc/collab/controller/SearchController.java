package com.poc.collab.controller;

import com.poc.collab.model.SearchDocument;
import com.poc.collab.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequiredArgsConstructor
public class SearchController {
    private final DocumentService service;
    @GetMapping("/api/search") public List<SearchDocument> search(@RequestParam String q) { return service.search(q); }
}
