package com.example.wdpai2backend.dto;

public class ProblemDto {
    String id_problem;
    Integer status;

    public ProblemDto(String id_problem, Integer status) {
        this.id_problem = id_problem;
        this.status = status;
    }

    public String getId_problem() {
        return id_problem;
    }

    public void setId_problem(String id_problem) {
        this.id_problem = id_problem;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
