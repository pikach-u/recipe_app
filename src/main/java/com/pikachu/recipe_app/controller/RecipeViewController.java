package com.pikachu.recipe_app.controller;

import com.pikachu.recipe_app.dto.RecipeDetailDto;
import com.pikachu.recipe_app.dto.RecipeResponseDto;
import com.pikachu.recipe_app.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeViewController {
    private final RecipeService recipeService;

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        int size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        Page<RecipeResponseDto> recipePage = recipeService.searchRecipe(null, pageable);

        model.addAttribute("recipes", recipePage.getContent());
        model.addAttribute("currentPage", page + 1);
        model.addAttribute("totalPages", recipePage.getTotalPages());

        return "recipe-list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        RecipeDetailDto recipeDetailDto = recipeService.get(id);
        model.addAttribute("recipe", recipeDetailDto);

        return "recipe-detail";
    }

}
