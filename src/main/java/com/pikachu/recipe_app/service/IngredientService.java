package com.pikachu.recipe_app.service;

import com.pikachu.recipe_app.dto.IngredientDto;
import com.pikachu.recipe_app.dto.IngredientResponseDto;
import com.pikachu.recipe_app.model.Ingredient;
import com.pikachu.recipe_app.repository.IngredientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public Page<IngredientResponseDto> list(Pageable pageable) {
        return ingredientRepository.findAll(pageable)
                .map(ingredient -> new IngredientResponseDto(
                        ingredient.getId(),
                        ingredient.getName()
                ));
    }

    public IngredientResponseDto get(Long id){
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(()->new NoSuchElementException("재료를 찾을 수 없습니다."));

        return new IngredientResponseDto(
                ingredient.getId(),
                ingredient.getName()
        );
    }

    public IngredientResponseDto create(IngredientDto dto) {
        if (ingredientRepository.findByName(dto.getName()).isPresent()) {
            throw new IllegalStateException("재료가 이미 존재합니다.");
        }
        Ingredient ingredient = new Ingredient();
        ingredient.setName(dto.getName());
        Ingredient saved = ingredientRepository.save(ingredient);
        return new IngredientResponseDto(saved.getId(), saved.getName());
    }

    public IngredientResponseDto update(Long id, IngredientDto dto){
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("재료가 존재하지 않습니다."));

        ingredient.setName(dto.getName());
        Ingredient saved = ingredientRepository.save(ingredient);

        return new IngredientResponseDto(saved.getId(), saved.getName());
    }

    public void delete(Long id){
        ingredientRepository.deleteById(id);
    }
}
