import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificateSubjectComponent } from './certificate-subject.component';

describe('CertificateSubjectComponent', () => {
  let component: CertificateSubjectComponent;
  let fixture: ComponentFixture<CertificateSubjectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CertificateSubjectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CertificateSubjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
