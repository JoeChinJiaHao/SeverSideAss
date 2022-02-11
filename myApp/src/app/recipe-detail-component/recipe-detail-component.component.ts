import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Recipe } from '../model';
import { RecipeService } from '../RecipeService.service';

@Component({
  selector: 'app-recipe-detail-component',
  templateUrl: './recipe-detail-component.component.html',
  styleUrls: ['./recipe-detail-component.component.css']
})
export class RecipeDetailComponentComponent implements OnInit {

  constructor(private recipeSvc:RecipeService,private aRoute:ActivatedRoute) { }
  recipe!:Recipe
  recipeId!:string
  errorMessage!:any
  gotError:boolean=false
  ngOnInit(): void {
    this.recipeId=this.aRoute.snapshot.params['recipeId']
    this.recipeSvc.getRecipe(this.recipeId)
                  .then(result=>{
                    console.log(result)
                    this.recipe=result
                    console.log(this.recipe)})
                  .catch(error=>{
                    this.errorMessage=error

                    this.gotError=true})
  }

}
