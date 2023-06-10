package com.chatgpt.controllers;

import com.chatgpt.entity.CreateHistoryRequest;
import com.chatgpt.entity.History;
import com.chatgpt.repositories.MessageRepository;
import com.chatgpt.services.HistoryService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class HistoryController {

    @Autowired
    HistoryService historyService;

    @Autowired
    MessageRepository messageRepository;

    @PostMapping(path = "/history")
    @RateLimiter(name = "historyLimit", fallbackMethod = "fallbackMethod")
    public ResponseEntity<History> createHistory(HttpServletRequest request, @RequestBody CreateHistoryRequest createHistoryRequest) throws Exception {
        return ResponseEntity.ok().body(historyService.createHistory((String) request.getAttribute("vkUserId"), createHistoryRequest));
    }


    @GetMapping(path = "/history")
    @RateLimiter(name = "historyLimit", fallbackMethod = "fallbackMethod")
    public ResponseEntity<Iterable<History>> getHistoryById(HttpServletRequest request) {
        return ResponseEntity.ok().body(historyService.getAllHistory((String) request.getAttribute("vkUserId")));
    }

    @DeleteMapping(path = "/history/{id}")
    @RateLimiter(name = "historyLimit", fallbackMethod = "fallbackMethod")
    @Transactional
    public ResponseEntity<String> deleteHistory(@PathVariable("id") UUID historyId) {
        historyService.deleteHistory(historyId);

        return ResponseEntity.ok().body("");
    }

    public ResponseEntity<Object> fallbackMethod(Exception e) {
        throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests");
    }
}