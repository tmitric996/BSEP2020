import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';  
import { HttpClientModule } from '@angular/common/http';  
import {DataTablesModule} from 'angular-datatables'; 

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddEntityComponent } from './add-entity/add-entity.component';
import { EntityListComponent } from './entity-list/entity-list.component';

@NgModule({
  declarations: [
    AppComponent,
    AddEntityComponent,
    EntityListComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    DataTablesModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
