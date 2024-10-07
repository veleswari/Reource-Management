import { TestBed } from '@angular/core/testing';

import { FlexFieldsService } from './flex-fields.service';

describe('FlexFieldsService', () => {
  let service: FlexFieldsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FlexFieldsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
