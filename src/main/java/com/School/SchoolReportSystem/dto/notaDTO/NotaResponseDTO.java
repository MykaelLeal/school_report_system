package com.School.SchoolReportSystem.dto.notaDTO;

import com.School.SchoolReportSystem.entitie.Nota;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotaResponseDTO {
    private String mensagem;
    private Nota nota;
}

