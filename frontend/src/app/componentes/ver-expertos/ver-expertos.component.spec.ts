import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerExpertosComponent } from './ver-expertos.component';

describe('VerExpertosComponent', () => {
  let component: VerExpertosComponent;
  let fixture: ComponentFixture<VerExpertosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VerExpertosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VerExpertosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
