import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { CertificateSubjectService } from '@services/certificate-subject.service';
import { CertificateSubject } from '@model/certificate-subject.model';

@Component({
  selector: 'app-certificate-subject-list',
  templateUrl: './certificate-subject-list.component.html',
  styleUrls: ['./certificate-subject-list.component.css']
})
export class CertificateSubjectListComponent implements OnInit {
  entityArray: any[] = [];
  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject();

  certificateSubjects: CertificateSubject[];
  deleteMessage = false;
  entitylist: any;
  isupdated = false;

  constructor(private certificateSubjectService: CertificateSubjectService) { }

  ngOnInit() {
    this.dtOptions = {
      pageLength: 6,
      stateSave: true,
      lengthMenu: [[6, 16, 20, -1], [6, 16, 20, "All"]],
      processing: true
    };

    this.certificateSubjectService.getCertificateSubjects().subscribe(data => {
      this.certificateSubjects = data;
      this.dtTrigger.next();
    })

  }

  delete(id: number) {
    this.certificateSubjectService.deleteCertificateSubject(id).subscribe(
      data => {
        this.deleteMessage = true;
        
        this.certificateSubjectService.getCertificateSubjects().subscribe(data => {
          this.certificateSubjects = data
        })
      });
  }

}
