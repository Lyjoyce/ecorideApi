
package com.example.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Le prénom est requis")
    private String firstname;

    @NotBlank(message = "Le nom est requis")
    private String lastname;

    @NotBlank(message = "L'email est requis")
    @Email(message = "Format d'email invalide")
    private String email;

    @NotBlank(message = "Le mot de passe est requis")
    private String password;

    @NotBlank(message = "Le numéro de téléphone est requis")
    private String telephone;

    private String role; // Peut être null pour défaut à ROLE_PASSAGER

    @NotNull(message = "Le nombre de places disponibles est requis")
    @Min(value = 1, message = "Minimum 1 place disponible")
    @Max(value = 4, message = "Maximum 4 places disponibles")
    private Integer seatAvailable;
}
