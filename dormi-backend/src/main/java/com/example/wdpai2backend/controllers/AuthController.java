package com.example.wdpai2backend.controllers;

import com.example.wdpai2backend.dto.LoginDto;
import com.example.wdpai2backend.dto.AuthResponseDto;
import com.example.wdpai2backend.dto.RegisterDto;
import com.example.wdpai2backend.entity.AppUser;
import com.example.wdpai2backend.entity.Authority;
import com.example.wdpai2backend.entity.Dormitory;
import com.example.wdpai2backend.entity.RefreshToken;
import com.example.wdpai2backend.kafka.KafkaTopicListener;
import com.example.wdpai2backend.repository.AuthorityRepository;
import com.example.wdpai2backend.repository.DormitoryRepository;
import com.example.wdpai2backend.repository.RefreshTokenRepository;
import com.example.wdpai2backend.repository.UserRepository;
import com.example.wdpai2backend.security.JwtService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    final private UserRepository userRepository;
    final private AuthorityRepository authorityRepository;

    final private AuthenticationManager authenticationManager;
    final private PasswordEncoder passwordEncoder;
    final private RefreshTokenRepository refreshRepository;

    final private JwtService jwtService;
    final private DormitoryRepository dormitoryRepository;

    @Autowired
    public AuthController(UserRepository userRepository, AuthorityRepository authorityRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RefreshTokenRepository refreshRepository, JwtService jwtService, DormitoryRepository dormitoryRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.refreshRepository = refreshRepository;
        this.jwtService = jwtService;
        this.dormitoryRepository = dormitoryRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto potentialUser) {
        if (userRepository.findByEmail(potentialUser.getEmail()).isPresent())
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);

        Authority authority = authorityRepository.findByAuthority("USER").get();
        HashSet set = new HashSet<Authority>();
        set.add(authority);
        Dormitory dormitory = dormitoryRepository.findByDorm_name(potentialUser.getDormitory()).get();
        if (dormitory == null) return new ResponseEntity<>("Dormitory not found", HttpStatus.BAD_REQUEST);
        AppUser user = new AppUser(potentialUser.getEmail(), passwordEncoder.encode(potentialUser.getPassword()), potentialUser.getUsername(), potentialUser.getSurname(), potentialUser.getUniversity(), dormitory, potentialUser.getRoom(), set);
        user = userRepository.save(user);
        return new ResponseEntity<>("User added successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto potentialUser) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(potentialUser.getEmail(), potentialUser.getPassword()));
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            jwtService.generateAccessToken(authentication);
            String token = jwtService.generateRefreshToken(authentication);
            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setRefreshToken(token);
            refreshToken.setExpiration(jwtService.getExpirationDateFromToken(token));
            refreshToken.setAppUser(userRepository.findByEmail(potentialUser.getEmail()).get());
            refreshRepository.save(refreshToken);
            AuthResponseDto authResponseDto = new AuthResponseDto(jwtService.generateAccessToken(authentication), jwtService.generateRefreshToken(authentication));
            System.out.println("Logged in");
            return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
        } else {
            System.out.println("Wrong credentials");
            return new ResponseEntity<>("Wrong credentials", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody String refreshToken) {
        try {
            System.out.println("77777777777");
            Gson gson = new Gson();
            JsonElement element = gson.fromJson(refreshToken, JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();
            String token = jsonObj.get("refreshToken").getAsString();
            RefreshToken tokenObject = refreshRepository.findByRefreshToken(token).get();
            if (tokenObject != null) {
                jwtService.refreshToken(tokenObject);
                AppUser user = tokenObject.getAppUser();
                System.out.println(user.getEmail());
                System.out.println(user.getPassword());
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                System.out.println(authentication.getName());
                System.out.println(token);
                AuthResponseDto authResponseDto = new AuthResponseDto(jwtService.generateAccessToken(authentication), token);
                return new ResponseEntity<>(authResponseDto, HttpStatus.OK);


            } else {
                new Exception("Refresh token not found");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/numberOfUser")
    public ResponseEntity<String> getIdUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser user = userRepository.findByEmail(authentication.getName()).get();
        String number = user.getId_user().toString();
        Gson gson = new Gson();
        return new ResponseEntity<>("{\"number\": " + user.getId_user() + ", \"topic\": \"" + user.generateTopic() + "\"}", HttpStatus.OK);
    }

    @RequestMapping(value = "/mylogout", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> hello(@Valid @RequestBody String refreshToken) {
        Gson gson = new Gson();
        JsonElement element = gson.fromJson(refreshToken, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        refreshToken = jsonObj.get("token").getAsString();
        try {
            RefreshToken token = refreshRepository.findByRefreshToken(refreshToken).get();
            if (token != null) {
                refreshRepository.delete(token);
                SecurityContextHolder.clearContext();
                return new ResponseEntity<>("Logout successful", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Refresh token not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
    }

}
