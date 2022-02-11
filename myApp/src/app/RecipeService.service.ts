
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { lastValueFrom } from "rxjs";
import { RecipeSummary } from "./model";

@Injectable()
export class RecipeService{
  constructor(private http:HttpClient){}

  getAllRecipes():Promise<any>{
    return lastValueFrom(this.http.get('http://localhost:8080/api/recipes'))
  }
  getRecipe(recipeId:string):Promise<any>{
   return lastValueFrom(this.http.get(`http://localhost:8080/api/recipe/${recipeId}`))
  }
}
