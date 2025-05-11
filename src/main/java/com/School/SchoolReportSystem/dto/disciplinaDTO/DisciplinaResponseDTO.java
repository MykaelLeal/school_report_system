package com.School.SchoolReportSystem.dto.disciplinaDTO;

import com.School.SchoolReportSystem.entitie.Disciplina;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisciplinaResponseDTO {
    
    private String mensagem;
    private Disciplina disciplina;
}

