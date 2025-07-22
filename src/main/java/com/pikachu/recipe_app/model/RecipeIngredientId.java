package com.pikachu.recipe_app.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RecipeIngredientId implements Serializable {

    // 합치는 table들의 ID
    private Long recipeId;
    private Long ingredientId;


}