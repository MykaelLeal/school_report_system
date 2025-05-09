package com.School.SchoolReportSystem.dto.userDTO;

import com.School.SchoolReportSystem.enums.RoleName;

public record CreateUserDto(

        String email,
        String password,
        RoleName role

) {
}
