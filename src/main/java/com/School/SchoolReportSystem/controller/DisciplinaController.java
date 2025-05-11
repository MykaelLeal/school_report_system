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

import com.School.SchoolReportSystem.dto.disciplinaDTO.DisciplinaDTO;
import com.School.SchoolReportSystem.dto.disciplinaDTO.DisciplinaResponseDTO;
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
    public ResponseEntity<DisciplinaResponseDTO> createDisciplina(@RequestBody DisciplinaDTO createDisciplinaDto) {
        Disciplina disciplina = disciplinaService.createDisciplina(createDisciplinaDto.getNome(), createDisciplinaDto.getProfessorId());
        DisciplinaResponseDTO response = new DisciplinaResponseDTO("Disciplina criada com sucesso.", disciplina);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        
        
    }

    // Buscar todas as Disciplinas
    @GetMapping("/")
    public ResponseEntity<List<Disciplina>> getDisciplinas() {
        return new ResponseEntity<>(disciplinaService.getAllDisciplinas(), HttpStatus.OK);
    }


    // Atualizar disciplina por ID
    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDTO> updateDisciplina(@PathVariable Long id, @RequestBody DisciplinaDTO disciplinaDTO) {
        // Verifica se a disciplina existe
        Disciplina disciplinaExist = disciplinaService.getDisciplinaById(id);
        DisciplinaResponseDTO response2 = new DisciplinaResponseDTO("Disciplina não encontrada.", disciplinaExist);
        if (disciplinaExist == null) { 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response2);
              
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
                 DisciplinaResponseDTO response3 = new DisciplinaResponseDTO("Não é o professor da disciplina.", disciplinaExist);
                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response3);
            }
            // Atribui o novo professor à disciplina
            disciplinaExist.setProfessor(professor);
        }
        // Salva a disciplina atualizada
        Disciplina disciplinaAtualizada = disciplinaService.saveDisciplina(disciplinaExist);
        // Retorna 200 OK com a disciplina atualizada
         DisciplinaResponseDTO response4 = new DisciplinaResponseDTO("Disciplina atualizada com sucesso.", disciplinaAtualizada);
         return ResponseEntity.status(HttpStatus.OK).body(response4);
    }

    // Busca disciplina por ID
    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> getDisciplina(@PathVariable Long id) {
        Disciplina disciplina = disciplinaService.getDisciplinaById(id);
        return new ResponseEntity<>(disciplina, HttpStatus.OK);
    }

   // Deleta disciplina por ID
   @DeleteMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDTO> deleteDisciplina(@PathVariable Long id) {
       Disciplina removida = disciplinaService.deleteDisciplina(id);
       DisciplinaResponseDTO response5 = new DisciplinaResponseDTO("Disciplina removida com sucesso.", removida);
       return ResponseEntity.ok(response5);
}





    
}

