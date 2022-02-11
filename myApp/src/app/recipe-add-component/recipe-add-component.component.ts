import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-recipe-add-component',
  templateUrl: './recipe-add-component.component.html',
  styleUrls: ['./recipe-add-component.component.css']
})
export class RecipeAddComponentComponent implements OnInit {

  constructor(private fb:FormBuilder,private router:Router) { }
  myForm!:FormGroup
  myIngArr!:FormArray
  ngOnInit(): void {
    this.myForm=this.fb.group({
      title:this.fb.control('',[Validators.required,Validators.minLength(3)]),
      ingredients:this.myIngArr,
      image:this.fb.control('',[Validators.required]),
      instructions:this.fb.control('',[Validators.required,Validators.minLength(3)])
    })
  }
  goBack(){
    this.router.navigate(['/'])
  }
}
