package com.example.Authentication.DTO;

import com.example.Authentication.Enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDTO {

    private String email;
    private String firstname ;
    private String lastname ;
    private Roles role;

}
