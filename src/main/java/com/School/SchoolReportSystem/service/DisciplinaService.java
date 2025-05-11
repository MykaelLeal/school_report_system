package com.School.SchoolReportSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.School.SchoolReportSystem.entitie.Disciplina;
import com.School.SchoolReportSystem.entitie.User;
import com.School.SchoolReportSystem.enums.RoleName;
import com.School.SchoolReportSystem.repository.DisciplinaRepository;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private UserService userService;

    // Método responsável por criar uma disciplina
    public Disciplina createDisciplina(String nome, Long idProfessor) {
        // Busca o usuário professor
        User professor = userService.getUserById(idProfessor);
        
        if (professor == null || professor.getRoles().stream().noneMatch(
            role -> role.getName().equals(RoleName.ROLE_PROFESSOR))) {
        throw new RuntimeException("Somente professores podem ser atribuídos a disciplinas.");
    }    

        // Criação e atribuição da disciplina
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(nome);
        disciplina.setProfessor(professor);

        // Salva a disciplina no repositório
        return disciplinaRepository.save(disciplina);
    }

    // Método responsável por buscar todas as disciplinas
    public List<Disciplina> getAllDisciplinas() {
        return disciplinaRepository.findAll();
    }

    // Método responsável por buscar disciplinas por ID
    public Disciplina getDisciplinaById(Long disciplinaId) {
        return disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada."));
    }

    // Método responsável por atualizar disciplina
    public Disciplina updateDisciplina(Long disciplinaId, String novoNome) {
        // Busca a disciplina pelo ID
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada."));
    
        // Atualiza o nome da disciplina
        disciplina.setNome(novoNome);
    
        // Salva a disciplina atualizada no banco
        return disciplinaRepository.save(disciplina);
    }

    // Método por salvar disciplina
    public Disciplina saveDisciplina(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }
    
    
    // Método responsável por deletar disciplinas por ID
    public Disciplina deleteDisciplina(Long disciplinaId) {
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
            .orElseThrow(() -> new RuntimeException("Disciplina não encontrada."));
    
        disciplinaRepository.delete(disciplina);
        return disciplina;
        
}

    
}

