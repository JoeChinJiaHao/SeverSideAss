package ibf2021.assessment.csf.server.controllers;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2021.assessment.csf.server.models.Recipe;
import ibf2021.assessment.csf.server.services.RecipeService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

/* Write your request hander in this file */
@RestController
public class RecipeRestController {
    @Autowired
    private RecipeService recipeSvc;

    @GetMapping(path="/api/recipe/{id}")
    public ResponseEntity<String> getOneRecipe(@PathVariable String id){
        Optional<Recipe> opt = recipeSvc.getRecipeById(id);
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        if(opt.isPresent()){
            JsonArrayBuilder Jarr = Json.createArrayBuilder();
            for(String i:opt.get().getIngredients()){
                Jarr.add(i);
            }
            
            JsonObject jO = Json.createObjectBuilder()
                                        .add("title", opt.get().getTitle())
                                        .add("id", opt.get().getId())
                                        .add("image", opt.get().getImage())
                                        .add("instructions",opt.get().getInstruction())
                                        .add("ingredients",Jarr)
                                        .build();
                                        
                                    

            return ResponseEntity.ok(jO.toString());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("recipe not found");
        }
        
    }
}