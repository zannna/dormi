package com.example.wdpai2backend.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;

public class KafkaTopicListener {

    @Autowired
    private KafkaListenerEndpointRegistrar registrar;

    public void addListenerForTopic(String topic, String groupId) {
        MethodKafkaListenerEndpoint<String, String> endpoint = new MethodKafkaListenerEndpoint<>();
        endpoint.setBean(this);
        endpoint.setMethod(getClass().getMethods()[0]); // Ustawia odpowiednią metodę słuchacza
        endpoint.setTopics(topic);
        endpoint.setId(groupId);

        registrar.registerEndpoint(endpoint);
    }



}