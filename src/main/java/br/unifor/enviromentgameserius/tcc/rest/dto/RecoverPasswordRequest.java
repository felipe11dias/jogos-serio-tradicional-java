package br.unifor.enviromentgameserius.tcc.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecoverPasswordRequest {

    @NotNull(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
}
