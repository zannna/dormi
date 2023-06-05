package com.example.wdpai2backend.controllers;

import com.example.wdpai2backend.entity.AppUser;
import com.example.wdpai2backend.entity.Problem;
import com.example.wdpai2backend.repository.ProblemRepository;
import com.example.wdpai2backend.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@RestController
@Service
public class ProblemController {

    ProblemRepository problemRepository;

    UserRepository userRepository;

    public ProblemController(ProblemRepository problemRepository, UserRepository userRepository) {
        this.problemRepository = problemRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/addProblem")
    ResponseEntity<Object> addProblem(@RequestBody String description) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(description, JsonObject.class);
        String desc = jsonObject.get("description").getAsString();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        AppUser appUser = userRepository.findByEmail(auth.getName()).get();

        if (appUser != null) {
            Problem problem = new Problem(desc, 1, appUser, appUser.getDormitory(), LocalDateTime.now());
            problemRepository.save(problem);
            return new ResponseEntity<>(problem.getId_problem(), HttpStatus.OK);
        }
        return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/userProblems")
    ResponseEntity<Object> getUserProblems() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = userRepository.findByEmail(auth.getName()).get();
        if (appUser != null) {

            List<Problem> problems = problemRepository.findUserSortedProblems(appUser.getEmail());
            System.out.println("userProblems");
            return new ResponseEntity<>(problems, HttpStatus.OK);
        }
        return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/unresolvedProblems")
    ResponseEntity<Object> getUnresolvedProblems() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Problem> problems = problemRepository.findUnresolvedProblems(userRepository.findIdDormitoryByEmail(auth.getName()));
        return new ResponseEntity<>(problems, HttpStatus.OK);

    }

    @PutMapping("/status")
    ResponseEntity<Object> changeProblemStatus(@RequestBody Problem problem) {
        if (problemRepository.updateProblemStatus(problem.getId_problem(), problem.getStatus()).isPresent())
            return new ResponseEntity<>("Status changed", HttpStatus.OK);

        return new ResponseEntity<>("Status not changed", HttpStatus.BAD_REQUEST);
    }

}
