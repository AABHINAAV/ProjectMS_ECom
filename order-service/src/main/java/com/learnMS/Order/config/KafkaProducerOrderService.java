package com.learnMS.Order.config;

import com.learnMS.Order.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaProducerOrderService {

    @Value("${spring.kafka.template.default-topic}")
    private String defaultTopicName;

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    public void publishMessageToKafkaCluster(Order msg) {
        kafkaTemplate.send(defaultTopicName, msg);
    }
}
