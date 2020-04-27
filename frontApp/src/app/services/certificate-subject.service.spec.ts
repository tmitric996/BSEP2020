import { TestBed } from '@angular/core/testing';

import { CertificateSubjectService } from './certificate-subject.service';

describe('CertificateSubjectService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CertificateSubjectService = TestBed.get(CertificateSubjectService);
    expect(service).toBeTruthy();
  });
});
