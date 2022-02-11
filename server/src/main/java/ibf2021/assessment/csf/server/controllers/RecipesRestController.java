package ibf2021.assessment.csf.server.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2021.assessment.csf.server.models.Recipe;
import ibf2021.assessment.csf.server.services.*;
import jakarta.json.Json;
/* Write your request hander in this file */
import jakarta.json.JsonArrayBuilder;
//import jakarta.json.JsonObject;

@RestController
@RequestMapping(path="/api/recipes", produces=MediaType.APPLICATION_JSON_VALUE)
public class RecipesRestController{
    @Autowired
    private RecipeService recipeSvc;

    @GetMapping
    public ResponseEntity<String> getAllTheRecipes(){
        JsonArrayBuilder jArr = Json.createArrayBuilder();
        //JsonObject jO = Json.createObjectBuilder().build();
        List<Recipe> myRecipeList =recipeSvc.getAllRecipes();
        for(Recipe r:myRecipeList){
            jArr.add(Json.createObjectBuilder()
                        .add("title",r.getTitle())
                        .add("id",r.getId())
                        .build());
        }
        
       
        return ResponseEntity.ok(jArr.build().toString());
    }
}