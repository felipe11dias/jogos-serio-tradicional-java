package br.unifor.enviromentgameserius.tcc.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotEmpty
    @Email
    private String username;

    @NotEmpty
    @Length(min = 8)
    public String password;
}
