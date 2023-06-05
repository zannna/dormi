package com.example.wdpai2backend.service;

import com.example.wdpai2backend.entity.Dormitory;
import com.example.wdpai2backend.repository.DormitoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DormitoryService {
    private DormitoryRepository dormitoryRepository;

    public DormitoryService(DormitoryRepository dormitoryRepository) {
        this.dormitoryRepository = dormitoryRepository;
    }

    public List<String> createKafkaIdOfAllDormitories() {
        List<Dormitory> dormitories= dormitoryRepository.findAll();
        List<String> names=new ArrayList<>();
        for (Dormitory dormitory : dormitories
             ) {
            names.add(dormitory.getId_dorm()+"dormitory");
        }
        return names;
    }
}
