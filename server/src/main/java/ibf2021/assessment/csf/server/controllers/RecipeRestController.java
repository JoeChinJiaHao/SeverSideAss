package ibf2021.assessment.csf.server.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonValue;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2021.assessment.csf.server.ServerApplication;
import ibf2021.assessment.csf.server.models.Recipe;
import ibf2021.assessment.csf.server.services.RecipeService;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;


/* Write your request hander in this file */
@RestController
public class RecipeRestController {
    @Autowired
    private RecipeService recipeSvc;
    
    private final Logger logger = Logger.getLogger(ServerApplication.class.getName());

    @PostMapping(path="api/recipe")
    public ResponseEntity<String> saveOneRecipe(@RequestBody String payload){
        logger.log(Level.INFO,">>>received");
        Recipe myRecipeobj = new Recipe();
        List<String> ingreList = new LinkedList<String>();
        logger.log(Level.INFO, ">>>>%s".formatted(payload));
        try(InputStream is = new ByteArrayInputStream(payload.getBytes(StandardCharsets.UTF_8))){
            JsonReader reader =Json.createReader(new BufferedReader(new InputStreamReader(is)));
            JsonObject data = reader.readObject();
            myRecipeobj.setImage(data.getJsonObject("image").toString());
            myRecipeobj.setInstruction(data.getJsonObject("instructions").toString());
            myRecipeobj.setTitle(data.getJsonObject("title").toString());
            JsonArray myIngArr=data.getJsonArray("ingredients");
            for(jakarta.json.JsonValue v:myIngArr){
                ingreList.add(v.toString());
                myRecipeobj.addIngredient(v.toString());
            }
            this.recipeSvc.addRecipe(myRecipeobj);
        }catch(Exception e){

        }
        
        
        

        logger.log(Level.INFO,">>>received end");



        return ResponseEntity.status(HttpStatus.CREATED).body("saved");
    }



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