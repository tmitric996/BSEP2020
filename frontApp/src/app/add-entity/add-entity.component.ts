import { Component, OnInit } from '@angular/core';
import {FormControl,FormGroup,Validators} from '@angular/forms';  
import { EntityService } from '../entity.service';
import { Entity } from '../entity';

@Component({
  selector: 'app-add-entity',
  templateUrl: './add-entity.component.html',
  styleUrls: ['./add-entity.component.css']
})
export class AddEntityComponent implements OnInit {

  constructor(private entityservice:EntityService) { }
  
  entity : Entity=new Entity();
  submitted = false;

  ngOnInit() {
    this.submitted = false;
  }

  entitysaveform = new FormGroup({
    entity_commonName:new FormControl('', [Validators.required, Validators.minLength(5)]),
    entity_type:new FormControl(),
    entity_surname:new FormControl(),
    entity_givenName:new FormControl(),
    entity_organization:new FormControl(),
    entity_orgUnitName:new FormControl(),
    entity_countryCode:new FormControl(),
    entity_email:new FormControl('', [Validators.required, Validators.email]),
  });

  saveEntity(saveEntity){
    this.entity = new Entity();
    this.entity.entity_commonName = this.EntityCommonName.value;
    this.entity.entity_type = this.EntityType.value;
    this.entity.entity_surname = this.EntitySurname.value;
    this.entity.entity_givenName = this.EntityGivenName.value;
    this.entity.entity_organization = this.EntityOrganization.value;
    this.entity.entity_orgUnitName = this.EntityOrgUnitName.value;
    this.entity.entity_countryCode = this.EntityCountryCode.value;
    this.entity.entity_email = this.EntityEmail.value;
    this.submitted=true;
    this.save();
  }

  save() {
    this.entityservice.createEntity(this.entity)
      .subscribe(data => console.log(data), error => console.log(error));
    this.entity = new Entity();
  }

  get EntityCommonName(){
    return this.entitysaveform.get('entity_commonName');
  }

  get EntityType(){
    return this.entitysaveform.get('entity_type');
  }

  get EntitySurname(){
    return this.entitysaveform.get('entity_surname');
  }

  get EntityGivenName(){
    return this.entitysaveform.get('entity_givenName');
  }

  get EntityOrganization(){
    return this.entitysaveform.get('entity_organization');
  }

  get EntityOrgUnitName(){
    return this.entitysaveform.get('entity_OrgUnitName');
  }

  get EntityCountryCode(){
    return this.entitysaveform.get('entity_countryCode');
  }

  get EntityEmail(){
    return this.entitysaveform.get('entity_email');
  }

  addEntityForm(){
    this.submitted = false;
    this.entitysaveform.reset();
  }

}
