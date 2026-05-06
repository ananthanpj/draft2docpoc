package com.poc.collab.service;

import com.poc.collab.dto.DocumentRequest;
import com.poc.collab.dto.EditRequest;
import com.poc.collab.model.*;
import com.poc.collab.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service @RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final VersionRepository versionRepository;
    private final AuditLogRepository auditLogRepository;
    private final SearchDocumentRepository searchDocumentRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public Document create(DocumentRequest req) {
        Instant now = Instant.now();
        Document doc = Document.builder().name(req.getName()).content(req.getContent()).ownerId(req.getOwnerId())
                .isPublic(req.getIsPublic() == null || req.getIsPublic())
                .permissionType(req.getPermissionType() == null ? PermissionType.PUBLIC : req.getPermissionType())
                .permittedUsers(req.getPermittedUsers() == null ? List.of() : req.getPermittedUsers())
                .createdAt(now).updatedAt(now).build();
        doc = documentRepository.save(doc);
        versionRepository.save(DocVersion.builder().documentId(doc.getId()).content(doc.getContent()).editedBy(doc.getOwnerId()).timestamp(now).build());
        index(doc); audit(doc.getId(), doc.getOwnerId(), AuditAction.CREATE); return doc;
    }

    public Document getById(String id) { return documentRepository.findById(id).orElseThrow(); }
    public List<DocVersion> versions(String id) { return versionRepository.findByDocumentIdOrderByTimestampDesc(id); }
    public DocVersion version(String id) { return versionRepository.findById(id).orElseThrow(); }
    public List<AuditLog> audit(String id) { return auditLogRepository.findByDocumentIdOrderByTimestampDesc(id); }

    public Document lock(String id, String userId) {
        Document doc = getById(id);
        if (!canAccess(doc, userId)) throw new RuntimeException("No permission");
        if (doc.getLockedBy() != null && !doc.getLockedBy().equals(userId)) throw new RuntimeException("Already locked");
        doc.setLockedBy(userId); doc.setLockedAt(Instant.now());
        doc = documentRepository.save(doc); audit(id, userId, AuditAction.LOCK);
        messagingTemplate.convertAndSend("/topic/document/" + id, "DOCUMENT_LOCKED"); return doc;
    }

    public Document unlock(String id, String userId) {
        Document doc = getById(id); if (doc.getLockedBy() == null) return doc;
        if (!doc.getLockedBy().equals(userId) && !doc.getOwnerId().equals(userId)) throw new RuntimeException("Cannot unlock");
        doc.setLockedBy(null); doc.setLockedAt(null); doc = documentRepository.save(doc);
        audit(id, userId, AuditAction.UNLOCK); messagingTemplate.convertAndSend("/topic/document/" + id, "DOCUMENT_UNLOCKED"); return doc;
    }

    public Document update(String id, EditRequest req) {
        Document doc = getById(id);
        if (!req.getUserId().equals(doc.getLockedBy())) throw new RuntimeException("Lock required");
        doc.setContent(req.getContent()); doc.setUpdatedAt(Instant.now()); doc = documentRepository.save(doc);
        versionRepository.save(DocVersion.builder().documentId(id).content(doc.getContent()).editedBy(req.getUserId()).timestamp(Instant.now()).build());
        audit(id, req.getUserId(), AuditAction.EDIT); index(doc);
        messagingTemplate.convertAndSend("/topic/document/" + id, doc); return doc;
    }

    public List<SearchDocument> search(String q) { return searchDocumentRepository.findByNameContainingOrContentContainingOrOwnerId(q, q, q); }

    private boolean canAccess(Document doc, String userId) {
        return doc.isPublic() || userId.equals(doc.getOwnerId()) || doc.getPermittedUsers().contains(userId);
    }
    private void index(Document d) { searchDocumentRepository.save(SearchDocument.builder().id(d.getId()).name(d.getName()).content(d.getContent()).ownerId(d.getOwnerId()).build()); }
    private void audit(String d, String u, AuditAction a) { auditLogRepository.save(AuditLog.builder().documentId(d).userId(u).action(a).timestamp(Instant.now()).build()); }

    @Scheduled(fixedDelay = 60000)
    public void cleanupStaleLocks() {
        Instant cutoff = Instant.now().minus(5, ChronoUnit.MINUTES);
        documentRepository.findAll().stream().filter(d -> d.getLockedAt() != null && d.getLockedAt().isBefore(cutoff)).forEach(d -> {
            d.setLockedBy(null); d.setLockedAt(null); documentRepository.save(d);
            messagingTemplate.convertAndSend("/topic/document/" + d.getId(), "DOCUMENT_UNLOCKED");
        });
    }
}
