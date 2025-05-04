package com.School.SchoolReportSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotaDto {
    private Double valor;
    private Long disciplinaId;
    private Long alunoId;
}

