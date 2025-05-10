package com.School.SchoolReportSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.School.SchoolReportSystem.entitie.Disciplina;
import com.School.SchoolReportSystem.entitie.Nota;
import com.School.SchoolReportSystem.entitie.User;
import com.School.SchoolReportSystem.enums.RoleName;
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

    public Nota createNota(Double valor, Long disciplinaId, Long alunoId) {
        // Busca o professor autenticado
        User professor = userService.getAuthenticatedUser();
        // Busca a disciplina no banco de dados
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                 // Verifica se ela existe
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada."));
        // Verifica se é o professor da disciplina 
        if (!disciplina.getProfessor().getId().equals(professor.getId())) {
            throw new RuntimeException("Você não é o professor dessa disciplina.");
        }
        
        // Verifica se é aluno
        User aluno = userService.getUserById(alunoId);
        if (aluno.getRoles().stream().noneMatch(role -> role.getName().equals(RoleName.ROLE_ALUNO))) {
           throw new RuntimeException("Só é possível lançar notas para alunos.");

        }

        // Cadastra a nota do aluno
        Nota nota = new Nota();
        nota.setValor(valor);
        nota.setDisciplina(disciplina);
        nota.setAluno(aluno);
        return notaRepository.save(nota);
    }

    // Método responsável por buscar as notas do aluno
    public List<Nota> getNotasDoAluno() {
        User aluno = userService.getAuthenticatedUser();
        return notaRepository.findByAluno(aluno);
    }

    // Método responsável por buscar as notas da disciplina
    public List<Nota> getNotasDaDisciplina(Long disciplinaId) {
        // Verifica se o usuário está autenticado
        User professor = userService.getAuthenticatedUser();
        // Busca a disciplina por ID
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada."));
        // Verifica se é o professor da disciplina       
        if (!disciplina.getProfessor().getId().equals(professor.getId())) {
            throw new RuntimeException("Você não é o professor dessa disciplina.");
        }
        return notaRepository.findByDisciplinaAndDisciplinaProfessor(disciplina, professor);
    }

    // Novo método: Atualizar nota por ID
    public Nota updateNota(Long notaId, Double novoValor) {
        User professor = userService.getAuthenticatedUser();
        Nota nota = notaRepository.findById(notaId)
                .orElseThrow(() -> new RuntimeException("Nota não encontrada."));

        // Verifica se o professor é o dono da disciplina dessa nota
        if (!nota.getDisciplina().getProfessor().getId().equals(professor.getId())) {
            throw new RuntimeException("Você não é o professor dessa disciplina.");
        }

        nota.setValor(novoValor);
        return notaRepository.save(nota);
    }

    // Novo método: Deletar nota por ID
    public void deleteNota(Long notaId) {
        User professor = userService.getAuthenticatedUser();
        Nota nota = notaRepository.findById(notaId)
                .orElseThrow(() -> new RuntimeException("Nota não encontrada."));

        if (!nota.getDisciplina().getProfessor().getId().equals(professor.getId())) {
            throw new RuntimeException("Você não é o professor dessa disciplina.");
        }

        notaRepository.delete(nota);
    }

    // Novo método: Atualizar nota de um aluno por disciplina
    public Nota updateNotaPorDisciplinaEAluno(Long disciplinaId, Long alunoId, Double novoValor) {
        User professor = userService.getAuthenticatedUser();
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada."));

        if (!disciplina.getProfessor().getId().equals(professor.getId())) {
            throw new RuntimeException("Você não é o professor dessa disciplina.");
        }

        User aluno = userService.getUserById(alunoId);
        if (aluno.getRoles().stream().noneMatch(role -> role.getName().equals(RoleName.ROLE_ALUNO))) {
            throw new RuntimeException("Usuário informado não é um aluno.");
        }

        Nota nota = notaRepository.findByDisciplinaAndAluno(disciplina, aluno)
                .orElseThrow(() -> new RuntimeException("Nota não encontrada para esse aluno na disciplina."));

        nota.setValor(novoValor);
        return notaRepository.save(nota);
    }
}


