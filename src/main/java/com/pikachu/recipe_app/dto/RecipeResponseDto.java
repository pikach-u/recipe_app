package com.pikachu.recipe_app.dto;

import com.pikachu.recipe_app.model.Ingredient;
import com.pikachu.recipe_app.model.Recipe;
import com.pikachu.recipe_app.model.RecipeCategory;
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
    private List<RecipeIngredientDto> recipeIngredientList;

    private RecipeCategory category;

    //엔티티를 그대로 반환하지 않고 응답용DTO에 Recipe를 인자로 가지는 생성자 추가 (검색용)
    public RecipeResponseDto(Recipe recipe) {
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.description = recipe.getDescription();
        this.category = recipe.getCategory();
        this.recipeIngredientList = recipe.getRecipeIngredients().stream()
                .map(recipeIngredient -> new RecipeIngredientDto(
                        recipeIngredient.getIngredient().getId(),
                        recipeIngredient.getIngredient().getName(),
                        recipeIngredient.getQuantity()
                ))
                .toList();
    }
}
