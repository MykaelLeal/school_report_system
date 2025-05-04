package com.School.SchoolReportSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.School.SchoolReportSystem.repository.DisciplinaRepository;
import com.School.SchoolReportSystem.repository.NotaRepository;

@Service
public class NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private UserService userService;

    public Nota cadastrarNota(Double valor, Long disciplinaId, Long alunoId) {
        User professor = userService.getAuthenticatedUser();
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada."));

        if (!disciplina.getProfessor().getId().equals(professor.getId())) {
            throw new RuntimeException("Você não é o professor dessa disciplina.");
        }

        User aluno = userService.getUserById(alunoId);
        if (!aluno.getRole().equals(Role.ALUNO)) {
            throw new RuntimeException("Só é possível lançar notas para alunos.");
        }

        Nota nota = new Nota();
        nota.setValor(valor);
        nota.setDisciplina(disciplina);
        nota.setAluno(aluno);
        return notaRepository.save(nota);
    }

    public List<Nota> getNotasDoAluno() {
        User aluno = userService.getAuthenticatedUser();
        return notaRepository.findByAluno(aluno);
    }

    public List<Nota> getNotasDaDisciplina(Long disciplinaId) {
        User professor = userService.getAuthenticatedUser();
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada."));
        if (!disciplina.getProfessor().getId().equals(professor.getId())) {
            throw new RuntimeException("Você não é o professor dessa disciplina.");
        }
        return notaRepository.findByDisciplinaAndDisciplinaProfessor(disciplina, professor);
    }
}

