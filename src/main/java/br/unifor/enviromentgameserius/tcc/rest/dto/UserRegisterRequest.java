package br.unifor.enviromentgameserius.tcc.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    private String role;

    @NotNull(message = "O nome não pode ser vazio.")
    @Length(min = 3, message = "O nome deve ter no minimo três caracters.")
    private String name;

    @NotNull(message = "O email não pode ser vazio.")
    @Email
    private String email;

    @NotNull(message = "A senha não pode ser vazia.")
    @Length(min = 8)
    private String password;

}
