package com.School.SchoolReportSystem.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.School.SchoolReportSystem.dto.userDTO.CreateUserDto;
import com.School.SchoolReportSystem.dto.userDTO.LoginUserDTO;
import com.School.SchoolReportSystem.dto.userDTO.RecoveryJwtTokenDto;
import com.School.SchoolReportSystem.entitie.User;
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
    public ResponseEntity<Map<String, String>> createUser(@RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        Map<String, String> msg = new HashMap<>();
        msg.put("mensagem", "Usuário criado com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);

    }

    // Buscar o usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
       User user = userService.getUserById(id);
       return ResponseEntity.ok(user);
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


}
