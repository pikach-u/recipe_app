package com.pikachu.recipe_app.service;

import com.pikachu.recipe_app.dto.*;
import com.pikachu.recipe_app.model.Ingredient;
import com.pikachu.recipe_app.model.Recipe;
import com.pikachu.recipe_app.model.RecipeIngredient;
import com.pikachu.recipe_app.model.RecipeIngredientId;
import com.pikachu.recipe_app.repository.IngredientRepository;
import com.pikachu.recipe_app.repository.RecipeIngredientRepository;
import com.pikachu.recipe_app.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

//    public Page<RecipeResponseDto> list(Pageable pageable) {
//        return recipeRepository.findAll(pageable)
//                .map(recipe -> new RecipeResponseDto(recipe.getId(), recipe.getTitle(), recipe.getDescription()));
//    }

    //조회 & 검색 - 조건이 null 또는 isBlank()면 전체 레시피 목록 조회
    public Page<RecipeResponseDto> searchRecipe(String title, Pageable pageable) {
        Specification<Recipe> specification = Specification.allOf();

        if(title != null && !title.isBlank()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("title"), "%" + title + "%"));
        }

        return recipeRepository.findAll(specification, pageable)
                .map(RecipeResponseDto::new);
    }

    public RecipeDetailDto get(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("레시피를 찾을 수 없습니다."));
        List<RecipeIngredientDto> ingredientDtos = recipe.getRecipeIngredients().stream()
                .map(
                        recipeIngredient -> new RecipeIngredientDto(
                                recipeIngredient.getIngredient().getId(),
                                recipeIngredient.getIngredient().getName(),
                                recipeIngredient.getQuantity()
                        )
                ).toList();

        return new RecipeDetailDto(recipe.getId(),
                recipe.getTitle(),
                recipe.getDescription(),
                ingredientDtos,
                recipe.getCategory());
    }

    public RecipeResponseDto create(RecipeDto dto) {
        Recipe recipe = new Recipe();
        recipe.setTitle(dto.getTitle());
        recipe.setDescription(dto.getDescription());
        recipe.setCategory(dto.getCategory());
        Recipe saved = recipeRepository.save(recipe);
        return new RecipeResponseDto(saved);
    }

    public RecipeResponseDto update(Long id, RecipeDto dto) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("레시피가 존재하지 않습니다."));
        recipe.setTitle(dto.getTitle());
        recipe.setDescription(dto.getDescription());
        recipe.setCategory(dto.getCategory());

        Recipe saved = recipeRepository.save(recipe);

        return new RecipeResponseDto(saved);
    }

    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }

    public void addIngredient(Long recipeId, AddIngredientDto dto) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NoSuchElementException("레시피를 찾을 수 없습니다."));
        Ingredient ingredient = ingredientRepository.findById(dto.getIngredientId())
                .orElseThrow(() -> new NoSuchElementException("재료를 찾을 수 없습니다."));

        RecipeIngredientId id = new RecipeIngredientId(recipeId, ingredient.getId());
        if (recipeIngredientRepository.existsById(id)) {
            throw new IllegalStateException("이미 추가된 재료입니다.");
        }

        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setId(id);
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setQuantity(dto.getQuantity());

        recipe.getRecipeIngredients().add(recipeIngredient);

        recipeRepository.save(recipe);

    }

    public void removeIngredient(Long recipeId, Long ingredientId) {
        RecipeIngredientId id = new RecipeIngredientId(recipeId, ingredientId);
        recipeIngredientRepository.deleteById(id);
    }
}
