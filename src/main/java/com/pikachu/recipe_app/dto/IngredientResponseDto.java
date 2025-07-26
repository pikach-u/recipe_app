package com.pikachu.recipe_app.dto;

import com.pikachu.recipe_app.model.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientResponseDto {
    private Long id;
    private String name;

    public IngredientResponseDto(Ingredient ingredient){
        this.name = ingredient.getName();
    }
}
