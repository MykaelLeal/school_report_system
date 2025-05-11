package com.School.SchoolReportSystem.dto.notaDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotaDTO {
    
    private Double valor;
    private Long disciplinaId;
    private Long alunoId;
}

