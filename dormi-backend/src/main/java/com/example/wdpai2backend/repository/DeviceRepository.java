package com.example.wdpai2backend.repository;

import com.example.wdpai2backend.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query("SELECT d FROM Device d WHERE d.dormitory.id_dorm = ?1 AND d.name_device = ?2")
    Optional<List<Device>> findDevicesOfDormitory(Long id_dorm, String name_device);

    @Query(nativeQuery = true, value="SELECT * FROM Device d WHERE d.name_device = ?1 AND d.number = ?2 AND d.id_dorm= ?3 LIMIT 1")
    Optional<Device> findDeviceByNameAndNumberAndDormitory(String name_device, Integer number, Long id_dorm);



}
