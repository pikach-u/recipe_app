package com.pikachu.recipe_app.dto;

import com.pikachu.recipe_app.model.Ingredient;
import com.pikachu.recipe_app.model.Recipe;
import com.pikachu.recipe_app.model.RecipeIngredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResponseDto {
    private Long id;
    private String title;
    private String description;
    private List<RecipeIngredient> recipeIngredientList;

    //엔티티를 그대로 반환하지 않고 응답용DTO에 Recipe를 인자로 가지는 생성자 추가 (검색용)
    public RecipeResponseDto(Recipe recipe) {
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.description = recipe.getDescription();
        this.recipeIngredientList = recipe.getRecipeIngredients();
    }
}
