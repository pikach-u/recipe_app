package com.pikachu.recipe_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDetailDto {
    private Long id;
    private String title;
    private String description;
    private List<RecipeIngredientDto> ingredients;
}
