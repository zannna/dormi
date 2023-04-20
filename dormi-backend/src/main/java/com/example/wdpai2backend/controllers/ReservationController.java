package com.example.wdpai2backend.controllers;

import com.example.wdpai2backend.dto.ReservationDto;
import com.example.wdpai2backend.dto.ResponseReservationDto;
import com.example.wdpai2backend.entity.AppUser;
import com.example.wdpai2backend.entity.Device;
import com.example.wdpai2backend.entity.Reservation;
import com.example.wdpai2backend.repository.DeviceRepository;
import com.example.wdpai2backend.repository.ReservationRepository;
import com.example.wdpai2backend.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    public ReservationController(ReservationRepository reservationRepository, UserRepository userRepository, DeviceRepository deviceRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
    }

    @GetMapping("/reservations")
    public ResponseEntity<Object> getReservations(@RequestBody String device) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(device, JsonObject.class);
        String dev = jsonObject.get("device").getAsString();
        Long id_dorm = userRepository.findIdDormitoryByEmail(auth.getName());
        List<Device> devices = deviceRepository.findDevicesOfDormitory(userRepository.findIdDormitoryByEmail(auth.getName()), dev).get();
        List<ResponseReservationDto> response = new ArrayList<>();
        LocalDateTime date = LocalDateTime.now();
        for (Device d : devices) {
            if (d.isWork()) {
                ResponseReservationDto res = new ResponseReservationDto(d.getNumber(), reservationRepository.findReservationOfDevice(d.getId_device(), date).get());
                response.add(res);
            }
        }
        if (response.isEmpty()) return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/addReservation")
    public ResponseEntity<String> addReservation(@RequestBody ReservationDto reservation) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = userRepository.findByEmail(auth.getName()).get();
        System.out.println(appUser.getDormitory().getId_dorm());
        Device device = deviceRepository.findDeviceByNameAndNumberAndDormitory(reservation.getDevice(), reservation.getNumberOfDevice(), appUser.getDormitory().getId_dorm()).get();
        if (device != null) {
            String startDate = reservation.getStartDate();
            LocalDateTime start = LocalDateTime.parse(startDate);
            String endDate = reservation.getEndDate();
            LocalDateTime end = LocalDateTime.parse(endDate);
            Reservation res = new Reservation(appUser, appUser.getDormitory(), device, end, start);
            try {
                reservationRepository.save(res);
            } catch (Exception ex) {

                return new ResponseEntity<>("Bad request ", HttpStatus.BAD_REQUEST);

            }
            return new ResponseEntity<>("Reservation added", HttpStatus.OK);
        }
        return new ResponseEntity<>("Bad request, device don't exist ", HttpStatus.BAD_REQUEST);


    }

    @DeleteMapping("/deleteReservation")
    public ResponseEntity<String> deleteReservation(@RequestBody String reservation) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(reservation, JsonObject.class);
        Long id_res = jsonObject.get("reservation").getAsLong();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long id_user = userRepository.findByEmail(auth.getName()).get().getId_user();
        Optional<Boolean> returned = reservationRepository.deleteReservation(id_user, id_res);
        if (returned.isEmpty())
            return new ResponseEntity<>("Bad request ", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>("Reservation deleted", HttpStatus.OK);

    }

    @GetMapping("/userReservations")
    public ResponseEntity<Object> getUserReservations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Reservation> list = reservationRepository.findByAppUser(auth.getName()).get();
        return new ResponseEntity<>(list, HttpStatus.OK);

    }


}
