package com.chatgpt.controllers;

import com.chatgpt.entity.CreateMessageRequest;
import com.chatgpt.entity.Message;
import com.chatgpt.services.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class MessageController {
    @Autowired
    MessageService messageService;

    @PostMapping(path = "/messages")
    @RateLimiter(name = "messagesLimit", fallbackMethod = "fallbackMethod")
    public ResponseEntity<Message> createMessage(HttpServletRequest request, @RequestBody CreateMessageRequest createMessageRequest) throws Exception {
        return ResponseEntity.ok().body(messageService.createMessage((String) request.getAttribute("vkUserId"), createMessageRequest));
    }

    @GetMapping(path = "/messages/{historyId}")
    @RateLimiter(name = "messagesLimit", fallbackMethod = "fallbackMethod")
    public ResponseEntity<Iterable<Message>> getMessages(HttpServletRequest request, @PathVariable("historyId") UUID historyId) throws Exception {
        return ResponseEntity.ok().body(messageService.getMessagesByHistoryId((String) request.getAttribute("vkUserId"), historyId));
    }

    @GetMapping(path = "/messages/json/{historyId}")
    @RateLimiter(name = "messagesLimit", fallbackMethod = "fallbackMethod")
    public ResponseEntity<ByteArrayResource> getMessagesJson(HttpServletRequest request, @PathVariable("historyId") UUID historyId) throws Exception {
        var messages = messageService.getMessagesByHistoryId((String) request.getAttribute("vkUserId"), historyId);

        ObjectMapper mapper = new ObjectMapper();
        byte[] jsonBytes = mapper.writeValueAsBytes(messages);

        ByteArrayResource resource = new ByteArrayResource(jsonBytes);


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.json")
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(jsonBytes.length)
                .body(resource);
    }
    @GetMapping(path = "/messages/txt/{historyId}")
    @RateLimiter(name = "messagesLimit", fallbackMethod = "fallbackMethod")
    public ResponseEntity<String> getMessagesTxt(HttpServletRequest request, @PathVariable("historyId") UUID historyId) throws Exception {
        var messages = messageService.getMessagesByHistoryId((String) request.getAttribute("vkUserId"), historyId).iterator();

        StringBuilder text = new StringBuilder();

        while (messages.hasNext()) {
            var message = messages.next();
            System.out.println(message);
            text.append(message.getRole()).append("\n\n").append(message.getContent()).append("\n\n");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.txt");

        return ResponseEntity.ok()
                .headers(headers)
                .body(text.toString());
    }

    public ResponseEntity<Object> fallbackMethod(Exception e) throws Exception {
        if (e != null) throw e;

        throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests");
    }
}
