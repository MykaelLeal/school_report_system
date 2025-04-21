package com.School.SchoolReportSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoletimDTO {
    private String disciplina;
    private Double nota;
    private String situacao; 
}
