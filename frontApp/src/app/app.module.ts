import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { HttpErrorInterceptor } from './shared/http-error.interceptor';
import { DataTablesModule } from 'angular-datatables';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { CertificateSubjectComponent } from './pages/certificate-subject/certificate-subject.component';
import { AddCertificateSubjectComponent } from '@components/add-certificate-subject/add-certificate-subject.component';
import { CertificateSubjectListComponent } from '@components/certificate-subject-list/certificate-subject-list.component';

@NgModule({
  declarations: [
    AppComponent,
    AddCertificateSubjectComponent,
    CertificateSubjectListComponent,
    CertificateSubjectComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    DataTablesModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: HttpErrorInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
