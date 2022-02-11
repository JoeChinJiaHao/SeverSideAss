import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import {  Router } from '@angular/router';

import { Recipe } from '../model';
import { RecipeService } from '../RecipeService.service';
import { v4 as uuidv4 } from 'uuid';
@Component({
  selector: 'app-recipe-add-component',
  templateUrl: './recipe-add-component.component.html',
  styleUrls: ['./recipe-add-component.component.css']
})
export class RecipeAddComponentComponent implements OnInit {

  constructor(private fb:FormBuilder,private router:Router,private recipeSvc:RecipeService) { }
  myForm!:FormGroup
  myIngArr!:FormArray
  valid!:boolean
  myReceipe!:Recipe

  ngOnInit(): void {
    this.myIngArr=this.fb.array([])
    this.myForm=this.fb.group({
      title:this.fb.control('water',[Validators.required,Validators.minLength(3)]),
      ingredientsArr:this.myIngArr,
      image:this.fb.control('happy',[Validators.required]),
      instructions:this.fb.control('friday',[Validators.required,Validators.minLength(3)])
    })

  }
  processForm(){
    const myValue=this.myForm.value
    let tempIngredients:string[]=[]
    console.log(myValue.ingredientsArr.length)
    for(let i:number=0; i<myValue.ingredientsArr.length;i++){
        console.log(myValue.ingredientsArr[i].ingredients)
        tempIngredients.push(myValue.ingredientsArr[i].ingredients);
    }
    console.log(tempIngredients)
    //this.myReceipe=myValue
    this.myReceipe={"title":myValue.title,"image":myValue.image,"instructions":myValue.instructions,"ingredients":tempIngredients,"id":uuidv4().toString().substring(0,8)}
    console.log(this.myReceipe)
    this.recipeSvc.saveRecipe(this.myReceipe).then(result=>{
                                              console.log(result)

    }).catch(error=>console.log(error))
  }
  addIngredients(){
    const IngGroup=this.fb.group({
      ingredients:this.fb.control('test',[Validators.required,Validators.minLength(3)])
    })
    this.myIngArr.push(IngGroup)
  }
  removeIng(i:number){
    this.myIngArr.removeAt(i)
  }
  goBack(){
    this.myForm.reset()
    this.router.navigate(['/'])
  }

}
