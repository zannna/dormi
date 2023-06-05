package com.example.wdpai2backend.controllers;

import com.example.wdpai2backend.dto.ReservationDto;
import com.example.wdpai2backend.dto.ResponseReservationDto;
import com.example.wdpai2backend.entity.AppUser;
import com.example.wdpai2backend.entity.Device;
import com.example.wdpai2backend.entity.Message;
import com.example.wdpai2backend.entity.Reservation;
import com.example.wdpai2backend.repository.DeviceRepository;
import com.example.wdpai2backend.repository.ReservationRepository;
import com.example.wdpai2backend.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    public ReservationController(ReservationRepository reservationRepository, UserRepository userRepository, DeviceRepository deviceRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
    }

//    @GetMapping("/reservations")
//    public ResponseEntity<Object> getReservations(@RequestBody String device) {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Gson gson = new Gson();
//        JsonObject jsonObject = gson.fromJson(device, JsonObject.class);
//        String dev = jsonObject.get("device").getAsString();
//        Long id_dorm = userRepository.findIdDormitoryByEmail(auth.getName());
//        List<Device> devices = deviceRepository.findDevicesOfDormitory(userRepository.findIdDormitoryByEmail(auth.getName()), dev).get();
//        List<ResponseReservationDto> response = new ArrayList<>();
//        LocalDateTime date = LocalDateTime.now();
//        for (Device d : devices) {
//            if (d.isWork()) {
//                ResponseReservationDto res = new ResponseReservationDto(d.getNumber(), reservationRepository.findReservationOfDevice(d.getId_device(), date).get());
//                response.add(res);
//            }
//        }
//        if (response.isEmpty()) return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping("/reservations/{device}")
    public ResponseEntity<Object> getReservations(@PathVariable String device) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Gson gson = new Gson();
        //  JsonObject jsonObject = gson.fromJson(device, JsonObject.class);
        //  String dev = jsonObject.get("device").getAsString();
        Long id_dorm = userRepository.findIdDormitoryByEmail(auth.getName());
        List<Device> devices = deviceRepository.findDevicesOfDormitory(userRepository.findIdDormitoryByEmail(auth.getName()), device).get();
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime previousDay = today;
        while (previousDay.getDayOfWeek() != DayOfWeek.MONDAY) {
            previousDay = previousDay.minusDays(1);
        }
        List<List<List<LocalDateTime[]>>> allReservations = new LinkedList<>();
        List<Integer> devicesNumbers = new LinkedList<>();
        LocalDate dates[][] = new LocalDate[4][2];
        int index = 0;
        for (int i = 0; i < 22; i = i + 7) {
            dates[index][0] = previousDay.plusDays(i).toLocalDate();
            dates[index][1] = previousDay.plusDays(i).plusDays(6).toLocalDate();
            index++;
        }
        for (Device d : devices) {
            if (d.isWork()) {
                devicesNumbers.add(d.getNumber());
                List<Reservation> monthReservations = reservationRepository.findReservationOfDevice(d.getId_device(), previousDay, previousDay.plusDays(30)).get();
                int i = 0;
                LocalDateTime newDate = previousDay;
                List<List<LocalDateTime[]>> formattedReservations = new LinkedList<>();
                while (i < 4) {
                    List<LocalDateTime[]> weekReservations = new LinkedList<>();
                    Iterator<Reservation> iter = monthReservations.iterator();
                    while (iter.hasNext()) {
                        Reservation res = iter.next();
                        LocalDateTime date = res.getStart_date();
                        LocalDateTime endDate = res.getEnd_date();
                        if (date.isBefore(newDate.plusDays(6).withHour(23).withMinute(59)) || date.isEqual(newDate.plusDays(6).withHour(23).withMinute(59))) {
                            weekReservations.add(new LocalDateTime[]{date, endDate});
                            ;
                            iter.remove();
                        } else break;
                    }
                    formattedReservations.add(weekReservations);
                    newDate = newDate.plusDays(7);
                    i++;
                }

                allReservations.add(formattedReservations);
            }

        }
        ResponseReservationDto response = new ResponseReservationDto(dates, devicesNumbers, allReservations);
        // if (response.isEmpty()) return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/addReservation")
    public ResponseEntity<String> addReservation(@RequestBody ReservationDto reservation) {
        String startDate = reservation.getStartDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        String endDate = reservation.getEndDate();
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);
        if (end.isBefore(start)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = userRepository.findByEmail(auth.getName()).get();
        Device device = deviceRepository.findDeviceByNameAndNumberAndDormitory(reservation.getDevice(), reservation.getNumberOfDevice(), appUser.getDormitory().getId_dorm()).get();
        if (device != null) {
            Reservation res = new Reservation(appUser, appUser.getDormitory(), device, start, end);
            try {
                reservationRepository.save(res);
            } catch (Exception ex) {

                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    }

    @DeleteMapping("/deleteReservation")
    public ResponseEntity<String> deleteReservation(@RequestBody String reservation) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(reservation, JsonObject.class);
        Long id_res = jsonObject.get("reservation").getAsLong();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long id_user = userRepository.findByEmail(auth.getName()).get().getId_user();
        Optional<Boolean> returned = reservationRepository.deleteReservation(id_user, id_res);
        if (returned.isEmpty()) return new ResponseEntity<>("Bad request ", HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>("Reservation deleted", HttpStatus.OK);

    }

    @GetMapping("/userReservations")
    public ResponseEntity<Object> getUserReservations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Reservation> list = reservationRepository.findByAppUser(auth.getName()).get();
        return new ResponseEntity<>(list, HttpStatus.OK);

    }


}
