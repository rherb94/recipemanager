package com.umuc.recipemanager.controller;

import com.umuc.recipemanager.domain.Recipe;
import com.umuc.recipemanager.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/recipes")

public class RecipeController {

    RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<Recipe> getAllRecipes() {
        log.info("Request to get all recipes.");
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable String id) {
        log.info("Request to get recipe with id: {}", id);
        return recipeService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        log.info("Request to create a new recipe.");
        return new ResponseEntity(recipeService.createRecipe(recipe), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public Recipe replaceRecipe(@RequestBody Recipe newRecipe, @PathVariable String id) {
        log.info("Request to update a recipe with id: {}", id);
        return recipeService.replaceRecipe(newRecipe);
    }

    @DeleteMapping("/{id}")
    public Recipe deleteRecipe(@PathVariable String id) {
        log.info("request to delete recipe with id: {}", id);
        Recipe recipe = recipeService.findById(id);
        recipeService.deleteRecipe(id);
        return recipe;
    }

}
