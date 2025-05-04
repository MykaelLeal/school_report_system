package com.School.SchoolReportSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.School.SchoolReportSystem.dto.CreateUserDto;
import com.School.SchoolReportSystem.dto.LoginUserDTO;
import com.School.SchoolReportSystem.dto.RecoveryJwtTokenDto;
import com.School.SchoolReportSystem.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Faz a autenticação do Usuário
    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDTO loginUserDto) {
        RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    // Cria o Usuário
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Testa se o endpoint de autenticação está funcionando
    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }

    // Testa se o endpoint de autenticação do Aluno está funcionando
    @GetMapping("/test/aluno")
    public ResponseEntity<String> getAlunoAuthenticationTest() {
        return new ResponseEntity<>("Aluno autenticado com sucesso", HttpStatus.OK);
    }

    // Testa se o endpoint de autenticação do Professor está funcionando
    @GetMapping("/test/professor")
    public ResponseEntity<String> getProfessorAuthenticationTest() {
        return new ResponseEntity<>("Professor autenticado com sucesso", HttpStatus.OK);
    }

    // Testa se o endpoint de autenticação do Admin está funcionando
    @GetMapping("/test/admin")
    public ResponseEntity<String> getAdminAuthenticationTest() {
        return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
    }

}
