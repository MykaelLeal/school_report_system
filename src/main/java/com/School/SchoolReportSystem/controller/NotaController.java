package com.School.SchoolReportSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.School.SchoolReportSystem.dto.notaDTO.NotaDTO;
import com.School.SchoolReportSystem.dto.notaDTO.NotaResponseDTO;
import com.School.SchoolReportSystem.entitie.Nota;
import com.School.SchoolReportSystem.service.NotaService;

@RestController
@RequestMapping("/notas")
public class NotaController {

    @Autowired
    private NotaService notaService;
    

    // Criar nota
    @PostMapping("/create")
    public ResponseEntity<NotaResponseDTO> createNota(@RequestBody NotaDTO notaDTO) {
        Nota notaCreate = notaService.createNota(notaDTO.getValor(), notaDTO.getDisciplinaId(), notaDTO.getAlunoId());
        NotaResponseDTO response = new NotaResponseDTO("Disciplina criada com sucesso.", notaCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // Buscar notas do aluno 
    @GetMapping("/aluno")
    public ResponseEntity<List<Nota>> getNotasDoAluno() {
        return ResponseEntity.ok(notaService.getNotasDoAluno());
    }


    // Buscar notas da disciplina pelo ID
    @GetMapping("/disciplina/{id}")
    public ResponseEntity<List<Nota>> getNotasDaDisciplina(@PathVariable Long id) {
        return ResponseEntity.ok(notaService.getNotasDaDisciplina(id));
    }


    // Atualizar nota por ID
    @PutMapping("/{id}")
    public ResponseEntity<NotaResponseDTO> updateNota(@PathVariable Long id, @RequestBody Double novoValor) {
        Nota updateNota = notaService.updateNota(id, novoValor);
        NotaResponseDTO response2 = new NotaResponseDTO("Nota atualizada com sucesso.", updateNota);
        return ResponseEntity.status(HttpStatus.OK).body(response2);
    }


    // Deletar nota por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<NotaResponseDTO> deleteNota(@PathVariable Long id) {
        Nota remove = notaService.deleteNota(id);
        NotaResponseDTO response3 = new NotaResponseDTO("Nota removida com sucesso.", remove);
        return ResponseEntity.status(HttpStatus.OK).body(response3);
    }


    // Atualizar nota por Disciplina e Aluno
    @PutMapping("/disciplina/{disciplinaId}/aluno/{alunoId}")
    public ResponseEntity<Nota> updateNotaPorDisciplinaEAluno(
            @PathVariable Long disciplinaId,
            @PathVariable Long alunoId,
            @RequestBody Double novoValor) {

        Nota notaAtualizada = notaService.updateNotaPorDisciplinaEAluno(disciplinaId, alunoId, novoValor);
        return ResponseEntity.ok(notaAtualizada);
    }
}
