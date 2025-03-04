import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerExpertoComponent } from '../ver-expertos/ver-expertos.component';

describe('VerExpertoComponent', () => {
  let component: VerExpertoComponent;
  let fixture: ComponentFixture<VerExpertoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VerExpertoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VerExpertoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
