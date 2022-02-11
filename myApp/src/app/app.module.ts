import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { RecipeListComponentComponent } from './recipe-list-component/recipe-list-component.component';
import { RecipeDetailComponentComponent } from './recipe-detail-component/recipe-detail-component.component';
import { RecipeAddComponentComponent } from './recipe-add-component/recipe-add-component.component';
import { RouterModule } from '@angular/router';
import {HttpClientModule} from '@angular/common/http'
const appRoutes=[
          {path:'',component:RecipeListComponentComponent},
          {path:'recipe/:recipeId',component:RecipeDetailComponentComponent},
          {path:'add',component:RecipeAddComponentComponent},
          {path:'**',redirectTo:""}]
@NgModule({
  declarations: [
    AppComponent,
    RecipeListComponentComponent,
    RecipeDetailComponentComponent,
    RecipeAddComponentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: environment.production,
      // Register the ServiceWorker as soon as the app is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
