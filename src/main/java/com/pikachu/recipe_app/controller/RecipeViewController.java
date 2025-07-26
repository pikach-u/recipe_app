package com.pikachu.recipe_app.controller;

import com.pikachu.recipe_app.dto.AddIngredientDto;
import com.pikachu.recipe_app.dto.RecipeDetailDto;
import com.pikachu.recipe_app.dto.RecipeDto;
import com.pikachu.recipe_app.dto.RecipeResponseDto;
import com.pikachu.recipe_app.model.RecipeCategory;
import com.pikachu.recipe_app.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeViewController {
    private final RecipeService recipeService;

    @GetMapping
    public String list(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        Page<RecipeResponseDto> recipePage = recipeService.searchRecipe(title, pageable);

        model.addAttribute("recipes", recipePage.getContent());
        model.addAttribute("currentPage", page + 1);
        model.addAttribute("totalPages", recipePage.getTotalPages());
        model.addAttribute("title", title);

        return "recipe-list";
    }

    @GetMapping("/{id}")
    public String detail(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int page,
            Model model) {
        RecipeDetailDto recipeDetailDto = recipeService.get(id);
        model.addAttribute("recipe", recipeDetailDto);
        model.addAttribute("currentPage", page);
        return "recipe-detail";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("recipe", new RecipeDto());
        model.addAttribute("categories", RecipeCategory.values());
        return "recipe-form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute RecipeDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return "recipe-form";
        }
        recipeService.create(dto);
        return "redirect:/recipes";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        RecipeDetailDto recipe = recipeService.get(id);
        RecipeDto dto = new RecipeDto();
        dto.setId(recipe.getId());
        dto.setTitle(recipe.getTitle());
        dto.setDescription(recipe.getDescription());
        dto.setCategory(recipe.getCategory());
        model.addAttribute("recipe", dto);
        model.addAttribute("categories", RecipeCategory.values());
        return "recipe-form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @Valid @ModelAttribute RecipeDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return "recipe-form";
        }
        recipeService.update(id, dto);
        return "redirect:/recipes/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        recipeService.delete(id);
        return "redirect:/recipes";
    }

    @PostMapping("/{id}/ingredients/add")
    public String addIngredient(
            @PathVariable Long id,
            @ModelAttribute AddIngredientDto dto
    ) {
        recipeService.addIngredient(id, dto);
        return "redirect:/recipes/" + id;
    }

    @PostMapping("/{id}/ingredients/{ingredientId}/remove")
    public String removeIngredient(
            @PathVariable Long id,
            @PathVariable Long ingredientId
    ) {
        recipeService.removeIngredient(id, ingredientId);
        return "redirect:/recipes/" + id;
    }


}
