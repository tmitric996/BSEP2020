import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';  
import { Observable } from 'rxjs'; 
import { CertificateSubject } from '@model/certificate-subject.model';

@Injectable({
  providedIn: 'root'
})
export class CertificateSubjectService {

  private subjectsURL = '/demo/digitalentity/'; //demo

  constructor(private http:HttpClient) { }

  createCertificateSubject(certificateSubject: CertificateSubject): Observable<object> {
    return this.http.post(`${this.subjectsURL}`, certificateSubject);
  }

  getCertificateSubjects(): Observable<CertificateSubject[]> {
    return this.http.get<CertificateSubject[]>(`${this.subjectsURL}`);
  }

  getCertificateSubject(id: number): Observable<CertificateSubject> {  
    return this.http.get<CertificateSubject>(`${this.subjectsURL}/${id}`);  
  }

  deleteCertificateSubject(id: number): Observable<any> {  
    return this.http.delete(`${this.subjectsURL}/${id}`);  
  } 

}