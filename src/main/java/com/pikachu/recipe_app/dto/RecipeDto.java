package com.pikachu.recipe_app.dto;

import com.pikachu.recipe_app.model.RecipeCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeDto {
    @NotBlank
    private String title;
    private String description;
    private RecipeCategory category;
}
