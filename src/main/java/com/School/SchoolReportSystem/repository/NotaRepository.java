package com.School.SchoolReportSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.School.SchoolReportSystem.entitie.Disciplina;
import com.School.SchoolReportSystem.entitie.Nota;
import com.School.SchoolReportSystem.entitie.User;

public interface NotaRepository extends JpaRepository<Nota, Long> {
    List<Nota> findByAluno(User aluno);
    List<Nota> findByDisciplinaAndDisciplinaProfessor(Disciplina disciplina, User professor);
}
