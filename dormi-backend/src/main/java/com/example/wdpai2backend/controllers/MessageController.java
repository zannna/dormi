package com.example.wdpai2backend.controllers;

import com.example.wdpai2backend.entity.AppUser;
import com.example.wdpai2backend.entity.Dormitory;
import com.example.wdpai2backend.entity.Message;
import com.example.wdpai2backend.repository.MessageRepository;
import com.example.wdpai2backend.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class MessageController {
    MessageRepository messageRepository;
    UserRepository userRepository;

    public MessageController(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/send")
    ResponseEntity<String> sendMessage(@RequestBody String sentMessage)
    {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(sentMessage, JsonObject.class);
        String mess = jsonObject.get("message").getAsString();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = userRepository.findByEmail(auth.getName()).get();
        Message message = new Message(appUser, mess, LocalDateTime.now());
        messageRepository.save(message);
        return new ResponseEntity<>("Message added", HttpStatus.OK);
    }
    @GetMapping("/all")
    ResponseEntity<Object> getAllMessages()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = userRepository.findByEmail(auth.getName()).get();
        List<Message> messages= messageRepository.findAllMessagesByDorm(appUser.getDormitory());
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

}
