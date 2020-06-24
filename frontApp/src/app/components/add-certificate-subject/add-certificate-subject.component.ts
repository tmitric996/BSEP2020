import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CertificateSubject } from '@model/certificate-subject.model';
import { CertificateSubjectService } from '@services/certificate-subject.service';

@Component({
  selector: 'app-add-certificate-subject',
  templateUrl: './add-certificate-subject.component.html',
  styleUrls: ['./add-certificate-subject.component.css']
})
export class AddCertificateSubjectComponent implements OnInit {

  constructor(private certificateSubjectService: CertificateSubjectService) { }

  certificateSubject = new CertificateSubject();
  certificateSubjectForm: any;

  submitted = false;

  ngOnInit() {
    this.submitted = false;

    this.certificateSubjectForm = new FormGroup({
      commonName: new FormControl(' ', [Validators.required, Validators.minLength(5)]),
      entityType: new FormControl(),
      surname: new FormControl(),
      givenName: new FormControl(),
      organization: new FormControl(),
      organizationUnitName: new FormControl(),
      countryCode: new FormControl(),
      email: new FormControl('', [Validators.required, Validators.email]),
    });
  }

  save() {
    console.log(this.certificateSubjectForm)
    if (this.certificateSubjectForm.valid) {
      this.certificateSubject = this.certificateSubjectForm.value;

      this.certificateSubjectService.createCertificateSubject(this.certificateSubject)
        .subscribe(data => console.log(data));

      this.certificateSubject = new CertificateSubject();
    }

  }

  addEntityForm() {
    this.submitted = false;
    this.certificateSubjectForm.reset();
  }

  hasError = (controlName: string, errorName: string) => {
    return this.certificateSubjectForm.controls[controlName].hasError(errorName);
  }

}
