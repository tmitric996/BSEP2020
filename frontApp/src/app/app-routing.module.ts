import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EntityListComponent } from './entity-list/entity-list.component';
import { AddEntityComponent } from './add-entity/add-entity.component';


const routes: Routes = [
  { path: '', redirectTo:'view-entity', pathMatch:'full' },
  { path: 'view-entity', component:EntityListComponent },
  { path: 'add-entity', component:AddEntityComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
