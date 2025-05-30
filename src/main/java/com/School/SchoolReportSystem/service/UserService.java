package com.School.SchoolReportSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.School.SchoolReportSystem.dto.userDTO.CreateUserDto;
import com.School.SchoolReportSystem.dto.userDTO.LoginUserDTO;
import com.School.SchoolReportSystem.dto.userDTO.RecoveryJwtTokenDto;
import com.School.SchoolReportSystem.entitie.Role;
import com.School.SchoolReportSystem.entitie.User;
import com.School.SchoolReportSystem.repository.UserRepository;
import com.School.SchoolReportSystem.security.auth.JwtTokenService;
import com.School.SchoolReportSystem.security.config.SecurityConfiguration;
import com.School.SchoolReportSystem.security.userdetails.UserDetailsImpl;


@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecoveryJwtTokenDto authenticateUser(LoginUserDTO loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    // Método responsável por criar um usuário
    public void createUser(CreateUserDto createUserDto) {
        if (userRepository.existsByEmail(createUserDto.email())) {
           throw new RuntimeException("Email já cadastrado.");
    }

        // Cria um novo usuário com os dados fornecidos
        User newUser = User.builder()
                .email(createUserDto.email())
                // Codifica a senha do usuário com o algoritmo bcrypt
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                // Atribui ao usuário uma permissão específica
                .roles(List.of(Role.builder().name(createUserDto.role()).build()))
                .build();

        // Salva o novo usuário no banco de dados
        userRepository.save(newUser);
    }

    // Busacr Usuário por ID
    public User getUserById(Long id) {
      return userRepository.findById(id)
        .orElseThrow();
}

    // Busca usuários autenticados
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() 
            || authentication.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("Usuário não autenticado.");
        }

        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow();
    }


}