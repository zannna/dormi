package com.example.wdpai2backend.repository;

import com.example.wdpai2backend.entity.AppUser;
import com.example.wdpai2backend.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "SELECT r FROM Reservation r WHERE r.device.id_device = ?1 AND r.start_date > ?2 AND r.end_date<?3 order by r.start_date")
    Optional<List<Reservation>> findReservationOfDevice(Long id_device, LocalDateTime date, LocalDateTime endDate);

    @Query(nativeQuery = true, value = "DELETE  FROM reservations WHERE id_user=?1 and id_res=?2 returning true")
    Optional<Boolean> deleteReservation(Long id_user, Long id_res);

    @Query("SELECT r FROM Reservation r WHERE r.appUser.email= ?1")
    Optional<List<Reservation>> findByAppUser(String email);

}
