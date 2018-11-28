package com.umuc.recipemanager.bootstrap;


import com.umuc.recipemanager.domain.Recipe;
import com.umuc.recipemanager.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RecipeRepositoryBoot implements ApplicationListener<ContextRefreshedEvent> {

    public RecipeRepository recipeRepository;

    @Autowired
    public RecipeRepositoryBoot(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // create some default recipes
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // check if any recipes are present in Mongo, if not create some
        if (recipeRepository.count() == 0) {
            createMockRecipes();
        }


    }

    private void createMockRecipes() {
        Recipe r = new Recipe();
        r.setContributorName("Abe Lincoln");
        r.setTitle("Abe's Chile");
        r.setInstructions("bla bla bla");
        r.setNotes("notes go here");
        recipeRepository.save(r);

        r = new Recipe();
        r.setContributorName("Teddy Roosevelt");
        r.setTitle("Teddy's Famous Ribs");
        r.setInstructions("bla bla bla");
        r.setNotes("notes go here");
        recipeRepository.save(r);

        r = new Recipe();
        r.setContributorName("Robert E Lee");
        r.setTitle("Bobby's Biscuits with Country Gravy");
        r.setInstructions("bla bla bla");
        r.setNotes("notes go here");
        recipeRepository.save(r);

        r = new Recipe();
        r.setContributorName("John F Kennedy");
        r.setTitle("Jack's Famous Clam Chowder");
        r.setInstructions("bla bla bla");
        r.setNotes("notes go here");
        recipeRepository.save(r);
    }
}
