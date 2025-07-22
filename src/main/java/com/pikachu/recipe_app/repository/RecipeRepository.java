package com.pikachu.recipe_app.repository;

import com.pikachu.recipe_app.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
