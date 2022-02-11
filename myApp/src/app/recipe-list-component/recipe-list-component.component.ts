import { Component, OnInit } from '@angular/core';
import { map } from 'rxjs';
import { RecipeSummary } from '../model';
import { RecipeService } from '../RecipeService.service';

@Component({
  selector: 'app-recipe-list-component',
  templateUrl: './recipe-list-component.component.html',
  styleUrls: ['./recipe-list-component.component.css']
})
export class RecipeListComponentComponent implements OnInit {

  constructor(private recipeSvc:RecipeService) { }
  recipeList!:RecipeSummary[]

  ngOnInit(): void {
    this.recipeSvc.getAllRecipes()
                  .then(result=>{
                    console.log(result)
                   this.recipeList=result
                  console.log(this.recipeList)})
  }

}
