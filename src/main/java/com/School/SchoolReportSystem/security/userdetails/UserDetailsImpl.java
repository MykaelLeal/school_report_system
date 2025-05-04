package com.School.SchoolReportSystem.security.userdetails;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.School.SchoolReportSystem.entitie.User;


import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserDetailsImpl implements UserDetails {

    private User user; 

     public UserDetailsImpl(User user) {
        this.user = user;
    }

    /*converte a lista de papéis (roles) associados ao usuário 
      em uma coleção de GrantedAuthorities, que é a forma que o Spring Security 
      usa para representar papéis. */
     @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    } // Retorna a credencial do usuário criada

    @Override
    public String getUsername() {
        return user.getEmail();
    } // Retorna o nome de usuário criado

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}