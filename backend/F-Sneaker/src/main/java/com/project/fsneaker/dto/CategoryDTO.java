package com.project.fsneaker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CategoryDTO {

    @NotBlank(message = "Category's name cannot be empty!")
    private String name;

}
