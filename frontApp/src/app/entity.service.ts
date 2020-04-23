import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';  
import { Observable } from 'rxjs'; 

@Injectable({
  providedIn: 'root'
})
export class EntityService {

  private baseUrl = 'http://localhost:8080/demo/digitalentity/';

  constructor(private http:HttpClient) { }

  getEntityList(): Observable<any> {
    return this.http.get(`${this.baseUrl}`+'entity-list');
  }

  createEntity(entity: object): Observable<object> {
    return this.http.post(`${this.baseUrl}`+'save-entity', entity);
  }

  deleteEntity(id: number): Observable<any> {  
    return this.http.delete(`${this.baseUrl}/delete-entity/${id}`, { responseType: 'text' });  
  } 

  getEntity(id: number): Observable<Object> {  
    return this.http.get(`${this.baseUrl}/entity/${id}`);  
  }


}
