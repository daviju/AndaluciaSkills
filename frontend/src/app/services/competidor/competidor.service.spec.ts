import { TestBed } from '@angular/core/testing';

import { CompetidorService } from './competidor.service';

describe('CompetidorService', () => {
  let service: CompetidorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompetidorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
