package com.pikachu.recipe_app.controller;

import com.pikachu.recipe_app.dto.IngredientResponseDto;
import com.pikachu.recipe_app.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ingredient")
public class IngredientViewController {
    private final IngredientService ingredientService;

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<IngredientResponseDto> ingredientPage = ingredientService.list(pageable);

        model.addAttribute("ingredients", ingredientPage.getContent());
        model.addAttribute("currentPage", page + 1);
        model.addAttribute("totalPages", ingredientPage.getTotalPages());

        return "ingredient-list";
    }
}
