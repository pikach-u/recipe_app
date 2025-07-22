package com.pikachu.recipe_app.repository;

import com.pikachu.recipe_app.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {
    // 고민
    Page<Recipe> findByTitleContaining(String title, Pageable pageable);
}
