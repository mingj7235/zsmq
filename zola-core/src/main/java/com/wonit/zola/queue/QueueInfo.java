package com.wonit.zola.queue;

import java.time.LocalDateTime;
import lombok.Value;

@Value(staticConstructor = "of")
public class QueueInfo {
    String name;
    int messagesAvailable;
    LocalDateTime created;
}