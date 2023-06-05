package com.example.wdpai2backend.controllers;

import com.example.wdpai2backend.entity.AppUser;
import com.example.wdpai2backend.entity.Message;
import com.example.wdpai2backend.kafka.KafkaTopicListener;
import com.example.wdpai2backend.repository.MessageRepository;
import com.example.wdpai2backend.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {
    MessageRepository messageRepository;
    UserRepository userRepository;
    private SimpMessagingTemplate messagingTemplate;
    private KafkaTemplate<String, String> kafkaTemplate;

    public MessageController(MessageRepository messageRepository, UserRepository userRepository, SimpMessagingTemplate messagingTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/messages/send")
    ResponseEntity<Object> sendMessage(@RequestBody String sentMessage) throws JsonProcessingException {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(sentMessage, JsonObject.class);
        String mess = jsonObject.get("message").getAsString();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = userRepository.findByEmail(auth.getName()).get();
        Message message = new Message(appUser, mess, LocalDateTime.now());
        message = messageRepository.save(message);
        String userTopic = appUser.generateTopic();
        message.setOwner(appUser.getFirstName() + "  " + appUser.getSurname());
        message.setNumberOfOwner(appUser.getId_user().toString());
        kafkaTemplate.send(userTopic, message.getJson());
        return new ResponseEntity<>(message.getId_mess(), HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<Object> getAllMessages() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = userRepository.findByEmail(auth.getName()).get();
        Long id = appUser.getId_user();
        List<Message> messages = messageRepository.findAllMessagesByDorm(appUser.getDormitory());
        Iterator<Message> iter = messages.iterator();
        while (iter.hasNext()) {
            Message m = iter.next();
            if (m.getAppUser().getId_user() != id) {
                m.setOwner(m.getAppUser().getFirstName() + "  " + m.getAppUser().getSurname());
                m.setNumberOfOwner(m.getAppUser().getId_user().toString());
            }
        }
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @MessageMapping("/chat")
    @KafkaListener(topics = "1dormitory")
    @KafkaListener(topics = "2dormitory")
    @KafkaListener(topics = "3dormitory")
    void getAllMessages(@Payload String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        String destination = "/topic/" + topic;
        System.out.println(destination);
        messagingTemplate.convertAndSend(destination, message);
    }


}
