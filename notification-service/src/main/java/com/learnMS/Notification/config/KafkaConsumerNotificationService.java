package com.learnMS.Notification.config;

import com.learnMS.Notification.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public class KafkaConsumerNotificationService {

    @KafkaListener(topics = "ProjectMS-ECom-Topic", groupId = "projectMsECom-groupId-1")
    public void readMessages(Order message) {
        log.info("msg log: " + message.toString());
        System.out.println("msg sout: " + message.toString());
    }
}
