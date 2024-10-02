package com.project.fsneaker.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginDTO {

    @NotBlank(message = "Phone number is required!")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "Password cannot be empty!")
    private String password;

    @NotNull(message = "Role ID is required!")
    @JsonProperty("role_id")
    private Long roleId;

}
