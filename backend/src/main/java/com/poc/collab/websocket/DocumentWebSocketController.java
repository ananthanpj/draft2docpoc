package com.poc.collab.websocket;

import com.poc.collab.dto.EditRequest;
import com.poc.collab.dto.LockRequest;
import com.poc.collab.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DocumentWebSocketController {
    private final DocumentService service;

    @MessageMapping("/document/{id}/edit")
    public void edit(@DestinationVariable String id, EditRequest request) { service.update(id, request); }

    @MessageMapping("/document/{id}/lock")
    public void lock(@DestinationVariable String id, LockRequest request) { service.lock(id, request.getUserId()); }

    @MessageMapping("/document/{id}/unlock")
    public void unlock(@DestinationVariable String id, LockRequest request) { service.unlock(id, request.getUserId()); }
}
