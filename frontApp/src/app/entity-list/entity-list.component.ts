import { Component, OnInit } from '@angular/core';
import { EntityService } from '../entity.service';
import { Subject, Observable } from 'rxjs';
import { Entity } from '../entity';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-entity-list',
  templateUrl: './entity-list.component.html',
  styleUrls: ['./entity-list.component.css']
})
export class EntityListComponent implements OnInit {

  constructor(private entityservice:EntityService) { }

  entityArray: any[] = [];
  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject();

  entities: Observable<Entity[]>;
  entity: Entity= new Entity();
  deleteMessage = false;
  entitylist:any;
  isupdated = false;

  ngOnInit() {
    this.isupdated = false;
    this.dtOptions = {
      pageLength: 6,
      stateSave: true,
      lengthMenu:[[6,16,20,-1],[6,16,20,"All"]],
      processing: true
    };
    this.entityservice.getEntityList().subscribe(data =>{
      this.entities = data;
      this.dtTrigger.next();
    })
  }

  deleteEntity(id: number){
    this.entityservice.deleteEntity(id)
      .subscribe(
        data => {
          console.log(data);
          this.deleteMessage = true;
          this.entityservice.getEntityList().subscribe(data =>{
            this.entities = data
          })
        },
        error => console.log(error));
  }

  
}
