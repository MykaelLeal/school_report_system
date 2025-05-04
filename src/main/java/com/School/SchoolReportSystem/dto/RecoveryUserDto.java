package com.School.SchoolReportSystem.dto;

import java.util.List;

import com.School.SchoolReportSystem.enums.RoleName;

public record RecoveryUserDto(

        Long id,
        String email,
        List<RoleName> roles

) {
}