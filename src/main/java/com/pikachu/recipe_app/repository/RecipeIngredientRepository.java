package com.pikachu.recipe_app.repository;

import com.pikachu.recipe_app.model.RecipeIngredient;
import com.pikachu.recipe_app.model.RecipeIngredientId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, RecipeIngredientId> {
}
