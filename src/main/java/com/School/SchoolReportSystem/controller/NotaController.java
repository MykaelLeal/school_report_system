package com.School.SchoolReportSystem.controller;

import com.School.SchoolReportSystem.entities.Nota;
import com.School.SchoolReportSystem.services.NotaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notas")
public class NotaController {

    private final NotaService notaService;

    @Autowired
    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    @PostMapping("/salvar")
    public Nota salvarNota(@RequestBody Nota nota, @RequestParam Long professorId) {
        return notaService.salvarNota(nota, professorId);
    }

    // Listar todas as notas
    @GetMapping
    public List<Nota> listarNotas() {
        return notaService.listarNotas();
    }

    // Buscar nota por ID
    @GetMapping("/{id}")
    public Nota buscarPorId(@PathVariable Long id) {
        return notaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Nota não encontrada"));
    }

    // Atualizar nota
    @PutMapping("/{id}")
    public Nota atualizarNota(@PathVariable Long id, @RequestBody Nota notaAtualizada) {
        return notaService.atualizarNota(id, notaAtualizada);
    }

    // Deletar nota
    @DeleteMapping("/{id}")
    public void deletarNota(@PathVariable Long id) {
        notaService.deletarNota(id);
    }

    // Listar notas de um aluno específico
    @GetMapping("/aluno/{alunoId}")
    public List<Nota> listarNotasPorAluno(@PathVariable Long alunoId) {
        return notaService.listarNotasPorAluno(alunoId);
    }

    // Listar notas por disciplina
    @GetMapping("/disciplina/{disciplinaId}")
    public List<Nota> listarNotasPorDisciplina(@PathVariable Long disciplinaId) {
        return notaService.listarNotasPorDisciplina(disciplinaId);
    }
}
