package com.example.example.controller;

import com.example.example.messaging.publisher.MessageSender;
import com.example.example.model.OrderedEvent;
import com.github.dhslrl321.zsmq.client.ZolaQueueMessageTemplate;
import com.github.dhslrl321.zsmq.listener.ZolaListenerContainer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SomeController {

    private final MessageSender sender;
    private final ZolaListenerContainer container;

    @GetMapping("/start")
    public boolean start() {
        container.listenAll();
        return true;
    }

    @PostMapping("/api/json")
    public String push(@RequestBody OrderRequest body) {
        OrderedEvent event = new OrderedEvent(body.userId, body.item, body.price);
        return sender.send(event);
    }

    @PostMapping("/api/text/{text}")
    public String push(@PathVariable String text) {
        //OrderedEvent event = new OrderedEvent(body.userId, body.item, body.price);
        return sender.send(text);
    }

    @Data
    @AllArgsConstructor
    private static class OrderRequest {
        Long userId;
        String item;
        Integer price;
    }
}