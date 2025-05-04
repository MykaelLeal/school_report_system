package com.School.SchoolReportSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.School.SchoolReportSystem.dto.CreateNotaDto;
import com.School.SchoolReportSystem.entitie.Nota;
import com.School.SchoolReportSystem.service.NotaService;

@RestController
@RequestMapping("/notas")
public class NotaController {

    @Autowired
    private NotaService notaService;

   @PostMapping
    public ResponseEntity<Nota> cadastrarNota(@RequestBody CreateNotaDto createNotaDto) {
        Nota nota = notaService.cadastrarNota(createNotaDto.getValor(), createNotaDto.getDisciplinaId(), createNotaDto.getAlunoId());
        return new ResponseEntity<>(nota, HttpStatus.CREATED);
    }

    @GetMapping("/aluno")
    public ResponseEntity<List<Nota>> getNotasDoAluno() {
        return new ResponseEntity<>(notaService.getNotasDoAluno(), HttpStatus.OK);
    }

    @GetMapping("/disciplina/{id}")
    public ResponseEntity<List<Nota>> getNotasDaDisciplina(@PathVariable Long id) {
        return new ResponseEntity<>(notaService.getNotasDaDisciplina(id), HttpStatus.OK);
    }
}

