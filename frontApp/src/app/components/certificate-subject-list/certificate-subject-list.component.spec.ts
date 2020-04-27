import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificateSubjectListComponent } from './certificate-subject-list.component';

describe('CertificateSubjectListComponent', () => {
  let component: CertificateSubjectListComponent;
  let fixture: ComponentFixture<CertificateSubjectListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CertificateSubjectListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CertificateSubjectListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
