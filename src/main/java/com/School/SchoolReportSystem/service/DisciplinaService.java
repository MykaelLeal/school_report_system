package com.School.SchoolReportSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.School.SchoolReportSystem.entitie.Disciplina;
import com.School.SchoolReportSystem.repository.DisciplinaRepository;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private UserService userService;

    public Disciplina criarDisciplina(String nome, Long idProfessor) {
        // Busca o usuário professor
        User professor = userService.getUserById(idProfessor);
        
        // Verifica se o usuário tem a role de professor
        if (professor == null || !professor.getRole().equals(RoleName.PROFESSOR)) {
            // Caso o professor não seja válido, lança uma exceção
            throw new RuntimeException("Somente professores podem ser atribuídos a disciplinas.");
        }

        // Criação e atribuição da disciplina
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(nome);
        disciplina.setProfessor(professor);

        // Salva a disciplina no repositório
        return disciplinaRepository.save(disciplina);
    }

    public List<Disciplina> listarDisciplinas() {
        // Retorna todas as disciplinas do repositório
        return disciplinaRepository.findAll();
    }
}

