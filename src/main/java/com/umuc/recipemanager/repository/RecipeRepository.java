package com.umuc.recipemanager.repository;


import com.umuc.recipemanager.domain.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<Recipe, String> {
}
