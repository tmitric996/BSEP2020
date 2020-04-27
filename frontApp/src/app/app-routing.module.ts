import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CertificateSubjectListComponent } from '@components/certificate-subject-list/certificate-subject-list.component';
import { AddCertificateSubjectComponent } from '@components/add-certificate-subject/add-certificate-subject.component';


const routes: Routes = [
  { path: '', redirectTo:'subjects', pathMatch:'full' },
  { path: 'subjects', component: CertificateSubjectListComponent },
  { path: 'subjects/create', component: AddCertificateSubjectComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
