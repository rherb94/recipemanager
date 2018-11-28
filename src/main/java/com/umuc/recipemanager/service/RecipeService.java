package com.umuc.recipemanager.service;

import com.umuc.recipemanager.domain.Recipe;
import com.umuc.recipemanager.exception.RecipeNotFoundException;
import com.umuc.recipemanager.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class RecipeService {

    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe findById(String id) {
        return recipeRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
    }

    public Recipe createRecipe(Recipe recipe) {
        Recipe newRecipe = new Recipe();
        newRecipe.setTitle(recipe.getTitle());
        newRecipe.setContributorName(recipe.getContributorName());
        newRecipe.setInstructions(recipe.getInstructions());
        newRecipe.setNotes(recipe.getNotes());

        return recipeRepository.save(newRecipe);
    }

    public Recipe replaceRecipe(Recipe recipe) {
        Recipe r = findById(recipe.getId());
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(String id) {
        recipeRepository.deleteById(id);
    }
}
