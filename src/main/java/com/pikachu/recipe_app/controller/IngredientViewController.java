package com.pikachu.recipe_app.controller;

import com.pikachu.recipe_app.dto.IngredientDto;
import com.pikachu.recipe_app.dto.IngredientResponseDto;
import com.pikachu.recipe_app.dto.RecipeDto;
import com.pikachu.recipe_app.model.Ingredient;
import com.pikachu.recipe_app.service.IngredientService;
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
@RequiredArgsConstructor
@RequestMapping("/ingredients")
public class IngredientViewController {
    private final IngredientService ingredientService;

    @GetMapping
    public String list(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<IngredientResponseDto> ingredientPage = ingredientService.list(pageable);

        model.addAttribute("ingredients", ingredientPage.getContent());
        model.addAttribute("currentPage", page + 1);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", ingredientPage.getTotalPages());
        model.addAttribute("name", name);

        return "ingredient-list";
    }

    @GetMapping("/new")
    public String create(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("ingredient", new IngredientDto());
        model.addAttribute("page", page);
        return "ingredient-form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute IngredientDto dto,
                         @RequestParam(defaultValue = "0") int page,
                         Model model,
                         BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("page", page);
            model.addAttribute("currentPage", page + 1);
            return "ingredient-form";
        }

        ingredientService.create(dto);
        return "redirect:/ingredients?page=" + page;
    }

    @GetMapping("/{id}/edit")
    public String edit(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        IngredientResponseDto ingredient = ingredientService.get(id);
        IngredientDto dto = new IngredientDto();

        dto.setId(ingredient.getId());
        dto.setName(ingredient.getName());

        model.addAttribute("ingredient", dto);
        model.addAttribute("page", page);
        model.addAttribute("currentPage", page + 1);

        return "ingredient-form";
    }

    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute IngredientDto dto,
            @RequestParam(defaultValue = "0") int page,
            Model model,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            model.addAttribute("page", page);
            model.addAttribute("currentPage", page + 1);
            return "ingredient-form";
        }
        ingredientService.update(id, dto);
        return "redirect:/ingredients?page=" + page;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        ingredientService.delete(id);
        return "redirect:/ingredients";
    }

}
