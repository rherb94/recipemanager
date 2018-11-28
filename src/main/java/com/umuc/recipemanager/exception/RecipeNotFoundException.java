package com.umuc.recipemanager.exception;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(String id) {
        super("Could not find recipe with id:  " + id);
    }
}
