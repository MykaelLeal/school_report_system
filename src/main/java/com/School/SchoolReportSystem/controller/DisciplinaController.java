package com.School.SchoolReportSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.School.SchoolReportSystem.dto.CreateDisciplinaDto;
import com.School.SchoolReportSystem.entitie.Disciplina;
import com.School.SchoolReportSystem.service.DisciplinaService;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @PostMapping
    public ResponseEntity<Disciplina> criarDisciplina(@RequestBody CreateDisciplinaDto createDisciplinaDto) {
        Disciplina disciplina = disciplinaService.criarDisciplina(createDisciplinaDto.getNome(), createDisciplinaDto.getProfessorId());
        return new ResponseEntity<>(disciplina, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Disciplina>> listarDisciplinas() {
        return new ResponseEntity<>(disciplinaService.listarDisciplinas(), HttpStatus.OK);
    }
    
}

