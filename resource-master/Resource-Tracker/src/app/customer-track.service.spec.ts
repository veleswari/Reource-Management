import { TestBed } from '@angular/core/testing';

import { CustomerTrackService } from './customer-track.service';

describe('CustomerTrackService', () => {
  let service: CustomerTrackService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CustomerTrackService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
