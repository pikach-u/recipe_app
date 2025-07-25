package com.pikachu.recipe_app.dto;

import com.pikachu.recipe_app.model.RecipeCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class RecipeDto {
    private Long id;

    @NotBlank
    private String title;
    private String description;
    private RecipeCategory category;
}
