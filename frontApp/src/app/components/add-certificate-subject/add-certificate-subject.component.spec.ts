import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCertificateSubjectComponent } from './add-certificate-subject.component';

describe('AddCertificateSubjectComponent', () => {
  let component: AddCertificateSubjectComponent;
  let fixture: ComponentFixture<AddCertificateSubjectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddCertificateSubjectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCertificateSubjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
