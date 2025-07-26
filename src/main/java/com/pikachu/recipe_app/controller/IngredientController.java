package com.pikachu.recipe_app.controller;

import com.pikachu.recipe_app.dto.IngredientDto;
import com.pikachu.recipe_app.dto.IngredientResponseDto;
import com.pikachu.recipe_app.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @GetMapping
    public Page<IngredientResponseDto> list(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        return ingredientService.list(name, pageable);
    }

    @PutMapping("/{id}")
    public IngredientResponseDto update(@PathVariable Long id, @Validated @RequestBody IngredientDto dto) {
        return ingredientService.update(id, dto);
    }

    @GetMapping("/{id}")
    public IngredientResponseDto get(@PathVariable Long id) {
        return ingredientService.get(id);
    }

    @PostMapping
    public IngredientResponseDto create(@Validated @RequestBody IngredientDto dto) {
        return ingredientService.create(dto);
    }
}
