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

import com.School.SchoolReportSystem.dto.DisciplinaDTO;
import com.School.SchoolReportSystem.entitie.Disciplina;
import com.School.SchoolReportSystem.entitie.User;
import com.School.SchoolReportSystem.enums.RoleName;
import com.School.SchoolReportSystem.service.DisciplinaService;
import com.School.SchoolReportSystem.service.UserService;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;
    private UserService userService;

    // Criar disciplina
    @PostMapping("/create")
    public ResponseEntity<Disciplina> createDisciplina(@RequestBody DisciplinaDTO createDisciplinaDto) {
        Disciplina disciplina = disciplinaService.createDisciplina(createDisciplinaDto.getNome(), createDisciplinaDto.getProfessorId());
        return new ResponseEntity<>(disciplina, HttpStatus.CREATED);
    }


    // Buscar todas as Disciplinas
    @GetMapping("/disciplinas")
    public ResponseEntity<List<Disciplina>> getDisciplinas() {
        return new ResponseEntity<>(disciplinaService.getAllDisciplinas(), HttpStatus.OK);
    }


    // Atualizar disciplina por ID
    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> updateDisciplina(@PathVariable Long id, @RequestBody DisciplinaDTO disciplinaDTO) {
        // Verifica se a disciplina existe
        Disciplina disciplinaExist = disciplinaService.getDisciplinaById(id);
        if (disciplinaExist == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Retorna 404 se não achar
        }
        // Atualiza o nome da disciplina
        disciplinaExist.setNome(disciplinaDTO.getNome());
        // Se veio professorId no DTO, valida se ele é professor
        if (disciplinaDTO.getProfessorId() != null) {
            User professor = userService.getUserById(disciplinaDTO.getProfessorId());
            // Verifica se professor existe e tem a role ROLE_PROFESSOR
            boolean isProfessor = professor != null && professor.getRoles()
                    .stream()
                    .anyMatch(role -> role.getName().equals(RoleName.ROLE_PROFESSOR));
            if (!isProfessor) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Retorna 400 se não for professor válido
            }
            // Atribui o novo professor à disciplina
            disciplinaExist.setProfessor(professor);
        }
        // Salva a disciplina atualizada
        Disciplina disciplinaAtualizada = disciplinaService.saveDisciplina(disciplinaExist);
        // Retorna 200 OK com a disciplina atualizada
        return new ResponseEntity<>(disciplinaAtualizada, HttpStatus.OK);
    }

    // Busca disciplina por ID
    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> getDisciplina(@PathVariable Long id) {
        Disciplina disciplina = disciplinaService.getDisciplinaById(id);
        return new ResponseEntity<>(disciplina, HttpStatus.OK);
    }

   // Deleta disciplina por ID
   @DeleteMapping("/{id}")
     public ResponseEntity<Void> deleteDisciplina(@PathVariable Long id) {
       disciplinaService.deleteDisciplina(id);
       return ResponseEntity.noContent().build(); 
}





    
}

