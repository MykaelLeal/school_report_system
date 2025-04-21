package com.School.SchoolReportSystem.controller;

import com.School.SchoolReportSystem.entities.Disciplina;
import com.School.SchoolReportSystem.services.DisciplinaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @PostMapping
    public Disciplina criarDisciplina(@RequestBody Disciplina disciplina) {
        return disciplinaService.salvarDisciplina(disciplina);
    }

    @GetMapping
    public List<Disciplina> listar() {
        return disciplinaService.listarDisciplinas();
    }

    @GetMapping("/{id}")
    public Optional<Disciplina> buscarPorId(@PathVariable Long id) {
        return disciplinaService.buscarPorId(id);
    }

    @GetMapping("/professor/{professorId}")
    public List<Disciplina> listarPorProfessor(@PathVariable Long professorId) {
        return disciplinaService.listarPorProfessor(professorId);
    }

    @PutMapping("/{id}")
    public Disciplina atualizar(@PathVariable Long id, @RequestBody Disciplina nova) {
        return disciplinaService.atualizarDisciplina(id, nova);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        disciplinaService.deletarDisciplina(id);
    }
}
